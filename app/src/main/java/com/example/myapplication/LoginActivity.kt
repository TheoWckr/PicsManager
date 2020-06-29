package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.DataBindingUtil
import com.example.myapplication.helpers.MetaI18n
import com.google.firebase.auth.FirebaseAuth
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.helpers.AllI18n
import com.example.myapplication.helpers.allI18n

class LoginActivity : AppCompatActivity() {
    class I18n(
        override val activityTitle: String,
        val loginButtonText: String,
        val emailPlaceholder: String,
        val passwordPlaceholder: String,
        val registerButtonText: String,
        val registerHint: String

    ) : MetaI18n


    private lateinit var loginButton : Button
    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.i18n = allI18n.login
        loginButton = findViewById(R.id.login)
        emailField = findViewById(R.id.editTextTextEmailAddress)
        emailField = findViewById(R.id.editTextTextEmailAddress)
        passwordField = findViewById(R.id.editTextTextPassword)

        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(baseContext, "u are signed in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(baseContext, "auth failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val auth = FirebaseAuth.getInstance()



    fun register(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}