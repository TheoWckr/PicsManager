package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.helpers.MetaI18n
import com.example.myapplication.helpers.allI18n
import com.example.myapplication.helpers.auth
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = auth.currentUser?.uid
        user?.let{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    class I18n(
        override val activityTitle: String,
        val loginButtonText: String,
        val emailPlaceholder: String,
        val passwordPlaceholder: String,
        val registerButtonText: String,
        val registerHint: String,
        val authSuccess: String,
        val authFailed: String

    ) : MetaI18n


    /**
     * UI elements
     * */
    private lateinit var loginButton : Button
    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText
    private lateinit var i18n : I18n

    /**
     * Handle the session and redirect into logged in if we are connected
     * */
    override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(this.firebaseAuthListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        i18n = allI18n.login
        binding.i18n = i18n

        loginButton = findViewById(R.id.login)
        emailField = findViewById(R.id.editTextTextEmailAddress)
        emailField = findViewById(R.id.editTextTextEmailAddress)
        passwordField = findViewById(R.id.editTextTextPassword)

        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(baseContext, i18n.authSuccess , Toast.LENGTH_SHORT).show()
                    val homeIntent = Intent(this, MainActivity::class.java)
                    startActivity(homeIntent)
                    finish()

                } else {
                    Toast.makeText(baseContext, i18n.authFailed, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun register(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}

