package com.example.planetpulse

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

class SignupActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainn)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = findViewById<EditText>(R.id.NameField)
        val sname = findViewById<EditText>(R.id.surnameField)
        val username = findViewById<EditText>(R.id.usernameField)
        val password = findViewById<EditText>(R.id.passwordField)
        val email = findViewById<EditText>(R.id.emaildField)
        val button = findViewById<Button>(R.id.SignupSubmitButton)

        db = FirebaseFirestore.getInstance()

        button.setOnClickListener {
            val nameText = name.text.toString().trim()
            val emailText = email.text.toString().trim()
            val userText = username.text.toString().trim()
            val passText = password.text.toString().trim()
            val snameText = sname.text.toString().trim()

            if (nameText.isEmpty() || emailText.isEmpty() || userText.isEmpty() || passText.isEmpty() || snameText == "Επιλογή Ρόλου") {
                Toast.makeText(this, "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("Users")
                .whereEqualTo("onxristi", userText)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        Toast.makeText(
                            this,
                            "Το όνομα χρήστη υπάρχει ήδη. Δοκίμασε άλλο.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val userMap = hashMapOf(
                            "name" to nameText,
                            "sname" to snameText,
                            "onxristi" to userText,
                            "kwd" to passText,
                            "email" to emailText,
                            "points" to 0,
                            "drasis" to "nodraseis1234"
                        )

                        db.collection("Users")
                            .add(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Εγγραφή επιτυχής", Toast.LENGTH_SHORT)
                                    .show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Αποτυχία εγγραφής", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Σφάλμα κατά τον έλεγχο username", Toast.LENGTH_SHORT)
                        .show()
                }
        }
        }
    }
