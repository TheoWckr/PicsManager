package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var registerBtn: Button
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var passwordConfirmField: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        println("firebase instance")
        println(auth.toString())
        registerBtn = findViewById(R.id.registerButton)
        emailField = findViewById(R.id.editTextTextEmailAddress)
        passwordField = findViewById(R.id.editTextTextPassword)
        passwordConfirmField = findViewById(R.id.editTextTextPasswordConfirm)



        registerBtn.setOnClickListener {

            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val passwordConfirm = passwordConfirmField.text.toString()

            if (email.isEmpty()) {
                emailField.error = "Votre mail doit etre rempli"
                emailField.requestFocus()
            }

            if (password.isEmpty()) {
                passwordField.error = "Veuillez saisir un mot de passe"
                passwordField.requestFocus()
            }

            if (password != passwordConfirm) {
                passwordConfirmField.error = "Vos deux mots de passes doivent correspondre"
                passwordConfirmField.requestFocus()
            }

            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm == password) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(
                                baseContext, "Authentication sucessfull.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }
        }

    }
}