package com.example.planyerpulse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        String onxristi = getIntent().getStringExtra("onxristi");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("onxristi", onxristi)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        long points = document.getLong("points"); // δεν έχει default

                        // Τώρα μπορείς να συνεχίσεις με τη δημιουργία των views:
                        setupCards(points, onxristi);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Σφάλμα φόρτωσης points", Toast.LENGTH_SHORT).show();
                });



    }
    private void setupCards(long points, String onxristi) {
        lLayout = findViewById(R.id.draseis);
        dataanalyzation dataanalyzation = new dataanalyzation();
        ArrayList<drasiCLASS> arrayList = dataanalyzation.dataAnalysation2(MainActivity2.this);

        for (int i = 0; i < arrayList.size(); i++) {
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

            view.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity2.this, drasidetails.class);
                intent.putExtra("titlosdrasis", textView1.getText());
                intent.putExtra("meros", textView2.getText());
                intent.putExtra("wra", textView3.getText());
                intent.putExtra("hmerohnia", textView4.getText());
                intent.putExtra("onxristi", onxristi);
                intent.putExtra("points", points);
                startActivity(intent);
            });
        }

        FloatingActionButton leaderboardbtn = findViewById(R.id.leaderboardbtn);
        leaderboardbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, LeaderboardActivity.class);
            intent.putExtra("onxristi", onxristi);
            intent.putExtra("points", points);
            startActivity(intent);
        });
    }



}