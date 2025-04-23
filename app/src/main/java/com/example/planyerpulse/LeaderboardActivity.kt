package com.example.planyerpulse

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LeaderboardActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var lLayout1: LinearLayout? = null
        lLayout1 = findViewById<LinearLayout>(R.id.people)
        for (i in 1..3) {
            val view = layoutInflater.inflate(R.layout.card1, null)
            val numberText1 = view.findViewById<TextView>(R.id.ar2)
            numberText1.text = i.toString()
            lLayout1.addView(view)
        }
        for (i in 4..14) {
            val view = layoutInflater.inflate(R.layout.card2, null)
            val numberText = view.findViewById<TextView>(R.id.numberTextView)
            numberText.text = i.toString()
            lLayout1.addView(view)
        }
    }
}