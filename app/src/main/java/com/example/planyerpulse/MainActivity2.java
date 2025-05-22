package com.example.planyerpulse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    LinearLayout lLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lLayout = findViewById(R.id.draseis);
        ArrayList<drasiCLASS> arrayList = dataAnalysation();
        for (int i = 0; i < arrayList.size(); i++){
            View view = getLayoutInflater().inflate(R.layout.card, null);
            lLayout.addView(view);
            TextView textView1 = view.findViewById(R.id.titlosdrasis);
            TextView textView2 = view.findViewById(R.id.Meros);
            TextView textView3 = view.findViewById(R.id.wra);
            TextView textView4 = view.findViewById(R.id.hmerohnia);
            textView1.setText(arrayList.get(i).action);
            textView2.setText(arrayList.get(i).meros);
            textView3.setText(arrayList.get(i).wra.toString());
            textView4.setText(arrayList.get(i).date.toString());
            //TextView textView4 = view.findViewById(R.id.titlosdrasis);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity2.this, drasidetails.class);
                    intent.putExtra("titlosdrasis", textView1.getText());
                    intent.putExtra("meros", textView2.getText());
                    intent.putExtra("wra", textView3.getText());
                    intent.putExtra("hmerohnia", textView4.getText());
                    //intent.putExtra("playnext",playnext); EXOUME KAI ALLA NA BALOUME
                    startActivity(intent);
                }
            });
        }
        FloatingActionButton leaderboardbtn = findViewById(R.id.leaderboardbtn);
        leaderboardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList dataAnalysation(){
        ArrayList<drasiCLASS> arrayList = new ArrayList<>();
        Context context = MainActivity2.this;
        List<List<String[]>> allFilesData = new ArrayList<>();
        List<List<String[]>> allFilesData1 = new ArrayList<>();
        List<List<String[]>> allFilesData2 = new ArrayList<>();
        try {
            String[] files = context.getAssets().list("");
            //
            for(String filename : files){
                if(filename.startsWith("all_2024_no_excel_file__NOx")){
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData.add(rows);
                }

                if(filename.startsWith("all_2024_so_excel_file__SO2")){
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData1.add(rows);
                }

                if(filename.startsWith("all_data_2024_co__CO")){
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData2.add(rows);
                }

            }
        } catch (IOException e) {}

        // εδω θα μπούνε τα analysation των data
        //
        double totalNo2 = 0;
        int countNo2 = 0;

        for (List<String[]> fileData : allFilesData) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double no2 = Double.parseDouble(row[4]);
                    totalNo2 += no2;
                    countNo2++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgNo2 = totalNo2 / countNo2;
        if (avgNo2>10){
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "<<Περπατάμε Μαζί>> "));
        }

        double totalSo2 = 0;
        int countSo2 = 0;

        for (List<String[]> fileData : allFilesData1) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double So2 = Double.parseDouble(row[2]);
                    totalSo2 += So2;
                    countSo2++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgSo2 = totalSo2 / countSo2;

        if (avgSo2>0.350){
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "<<Εργαστήρι Καθαρού Αέρα>>"));
        }

        double totalCo = 0;
        int countCo = 0;

        for (List<String[]> fileData : allFilesData2) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double Co = Double.parseDouble(row[4]);
                    totalCo += Co;
                    countCo++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgCo = totalCo / countCo;
        if (avgCo>1){
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "Ζεσταίνουμε αλλιώς"));
        }

        return arrayList;
    }
}