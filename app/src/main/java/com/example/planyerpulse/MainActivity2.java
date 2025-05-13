package com.example.planyerpulse;

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
        for (int i = 0; i < 15; i++){
            View view = getLayoutInflater().inflate(R.layout.card, null);
            lLayout.addView(view);
            TextView textView1 = view.findViewById(R.id.titlosdrasis);
            TextView textView2 = view.findViewById(R.id.Meros);
            TextView textView3 = view.findViewById(R.id.wra);
            TextView textView4 = view.findViewById(R.id.hmerohnia);
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
        Button leaderboardbtn = findViewById(R.id.leaderboard);
        leaderboardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
    }
}