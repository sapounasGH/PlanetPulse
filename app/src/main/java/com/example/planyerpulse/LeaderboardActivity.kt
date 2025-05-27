package com.example.planyerpulse

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

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
        val lLayout1 = findViewById<LinearLayout>(R.id.people)
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .orderBy("points", Query.Direction.DESCENDING)  // Ταξινόμηση φθίνουσα
            .get()
            .addOnSuccessListener { result ->
                var index = 1
                for (document in result) {
                    val onxristi = document.getString("onxristi") ?: ""
                    val points = document.getLong("points")?.toInt() ?: 0

                    val view = if (index <= 3) {
                        layoutInflater.inflate(R.layout.card1, null)
                    } else {
                        layoutInflater.inflate(R.layout.card2, null)
                    }

                    // Βάλε αριθμό θέσης
                    val numberText = view.findViewById<TextView>(
                        if (index <= 3) R.id.ar2 else R.id.numberTextView
                    )
                    numberText.text = index.toString()

                    // Βάλε όνομα χρήστη
                    val nameText = view.findViewById<TextView>(
                        if (index <= 3) R.id.Meros else R.id.Meros1
                    )
                    nameText.text = onxristi

                    lLayout1?.addView(view)
                    index++
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Σφάλμα: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
