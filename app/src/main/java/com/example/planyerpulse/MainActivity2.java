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



}