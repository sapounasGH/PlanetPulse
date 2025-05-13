package com.example.planyerpulse;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class drasidetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        TextView textView1 = findViewById(R.id.textView2);
        textView1.setText(titlosdrasis);
        TextView textView2 = findViewById(R.id.textView3);
        textView2.setText(hmerohnia);
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText(wra);
        TextView textView5 = findViewById(R.id.textView5);
        textView5.setText(meros);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng thessaloniki = new LatLng(40.6401, 22.9444);
                    googleMap.addMarker(new MarkerOptions().position(thessaloniki).title("Thessaloniki"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thessaloniki, 12f));
                }
            });
        }
    }
}