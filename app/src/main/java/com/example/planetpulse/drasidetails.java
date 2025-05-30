package com.example.planetpulse;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class drasidetails extends AppCompatActivity {

    private FirebaseFirestore db;
    private String onxristi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        onxristi = getIntent().getStringExtra("onxristi");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drasidetails);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        String titlosdrasis=getIntent().getStringExtra("titlosdrasis");
        String meros=getIntent().getStringExtra("meros");
        String wra=getIntent().getStringExtra("wra");
        String hmerohnia=getIntent().getStringExtra("hmerohnia");
        String onxristi=getIntent().getStringExtra("onxristi");
        TextView textView1 = findViewById(R.id.textView2);
        textView1.setText(titlosdrasis);
        TextView textView2 = findViewById(R.id.textView3);
        textView2.setText(hmerohnia);
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText(wra);
        TextView textView5 = findViewById(R.id.textView5);
        textView5.setText(meros);
        TextView textView7 = findViewById(R.id.textView7);
        textView7.setText(onxristi);
        Double langitude=getIntent().getDoubleExtra("longitude",0.0);
        Double latitude=getIntent().getDoubleExtra("latitude",0.0);
        System.out.println(latitude+" "+langitude);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng thessaloniki = new LatLng(latitude, langitude);
                    googleMap.addMarker(new MarkerOptions().position(thessaloniki).title("Thessaloniki"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thessaloniki, 12f));
                    System.out.println();
                }
            });
        }
        Button pointButton = findViewById(R.id.button2);
        pointButton.setOnClickListener(v -> {
            if (onxristi != null) {
                db.collection("Users")
                        .whereEqualTo("onxristi", onxristi)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentReference userRef = queryDocumentSnapshots.getDocuments().get(0).getReference();
                                userRef.update("points", FieldValue.increment(1))
                                        .addOnSuccessListener(unused -> {
                                            userRef.update("drasis", getIntent().getStringExtra("drasiID"))
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d("Firestore", "Η τιμή του πεδίου 'name' έχει ενημερωθεί");
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("Firestore", "Σφάλμα στην ενημέρωση του πεδίου", e);
                                                    });

                                            Toast.makeText(drasidetails.this, "Πόντος προστέθηκε!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(drasidetails.this, MainActivity2.class);
                                            intent.putExtra("onxristi", onxristi);
                                            intent.putExtra("points", getIntent().getLongExtra("points", 0));
                                            finishAffinity();
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(drasidetails.this, "Σφάλμα: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(drasidetails.this, "Σφάλμα σύνδεσης με βάση", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Δεν βρέθηκε χρήστης!", Toast.LENGTH_SHORT).show();
            }


        });


    }

}