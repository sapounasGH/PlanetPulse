package com.example.planyerpulse

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginSubmitButton: Button
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)
        loginSubmitButton = findViewById(R.id.loginSubmitButton)

        loginSubmitButton.setOnClickListener {
            val uname = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (uname.isNotEmpty() && password.isNotEmpty()) {
                db.collection("Users")
                    .whereEqualTo("onxristi", uname)
                    .whereEqualTo("kwd", password)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val document = documents.documents[0]
                            val name = document.getString("name") ?: ""
                            val sname = document.getString("sname") ?: ""
                            val onxristi = document.getString("onxristi") ?: ""
                            val email = document.getString("email") ?: ""
                            val kwd = document.getString("kwd") ?: ""
                            val points = document.getLong("points") ?: ""
                            val drasi = document.getString("drasis") ?: ""
                            // Στέλνουμε στην επόμενη σελίδα
                            val intent = Intent(this, MainActivity2::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("points", points)
                            intent.putExtra("sname", sname)
                            intent.putExtra("onxristi", onxristi)
                            intent.putExtra("email", email)
                            intent.putExtra("kwd", kwd)
                            intent.putExtra("drasis", drasi)
                            val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            sharedPref.edit().apply {
                                putString("loggedUser", onxristi)
                                putString("loggedName", name)
                                apply()
                            }
                            finishAffinity()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Λάθος όνομα χρήστη ή κωδικός", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Σφάλμα: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show()
            }
        }

    }
}