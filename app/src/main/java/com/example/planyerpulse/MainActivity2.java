package com.example.planyerpulse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity2 extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayout lLayout;
    long points;
    String onxristi;
    String drasiID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onxristi = getIntent().getStringExtra("onxristi");
        drasiID=getIntent().getStringExtra("drasis");
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
                        points = document.getLong("points");

                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Σφάλμα φόρτωσης points", Toast.LENGTH_SHORT).show();
                });
        FloatingActionButton leaderboardbtn = findViewById(R.id.leaderboardbtn);
        leaderboardbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, LeaderboardActivity.class);
            intent.putExtra("onxristi", onxristi);
            intent.putExtra("points", points);
            startActivity(intent);
        });
        System.out.println(drasiID);
        setupCards(drasiID);
        System.out.println("Eginan oi kartes!!");
    }
    private void setupCards(String drasid) {
        lLayout = findViewById(R.id.draseis);
        db.collection("drasi")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        drasiCLASS item = document.toObject(drasiCLASS.class);
                        View view = getLayoutInflater().inflate(R.layout.card, null);
                        Timestamp timestamp= item.getDateTime();
                        Date date= timestamp.toDate();
                        GeoPoint gp=item.getmapsPlace();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                        String dateString = sdf.format(date);
                        String dateTimeString = sdf2.format(date);
                        lLayout.addView(view);
                        TextView textView1 = view.findViewById(R.id.titlosdrasis);
                        TextView textView2 = view.findViewById(R.id.Meros);
                        TextView textView3 = view.findViewById(R.id.wra);
                        TextView textView4 = view.findViewById(R.id.hmerohnia);
                        textView1.setText(item.name);
                        textView2.setText(item.place);
                        textView3.setText(dateTimeString);
                        textView4.setText(dateString);
                        if(!document.getId().equals(drasid)){
                                view.setOnClickListener(v -> {
                                Intent intent = new Intent(MainActivity2.this, drasidetails.class);
                                intent.putExtra("titlosdrasis", textView1.getText());
                                intent.putExtra("meros", textView2.getText());
                                intent.putExtra("wra", textView3.getText());
                                intent.putExtra("hmerohnia", textView4.getText());
                                intent.putExtra("onxristi", onxristi);
                                intent.putExtra("points", points);
                                intent.putExtra("longitude", gp.getLongitude());
                                intent.putExtra("latitude", gp.getLatitude());
                                intent.putExtra("drasiID", document.getId());
                                startActivity(intent);
                            });
                        }else{view.setEnabled(false);view.setAlpha(0.5f);}
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Σφάλμα στην ανάκτηση: ", e);
                });

    }



}