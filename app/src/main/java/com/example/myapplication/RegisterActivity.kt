package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.helpers.MetaI18n
import com.example.myapplication.helpers.allI18n
import com.example.myapplication.helpers.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBtn: Button
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var passwordConfirmField: EditText

    class I18n(
        override val activityTitle: String,
        val emailPlaceholder: String,
        val passwordPlaceholder: String,
        val passwordConfirmPlaceholder: String,
        val emailEmptyError: String,
        val passwordEmptyError: String,
        val passwordDontMatchError: String,
        val authSuccess: String,
        val authFailed: String,
        val sigupButtonText: String

    ) : MetaI18n


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i18n = allI18n.register
        val binding: ActivityRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)
        setContentView(R.layout.activity_register)
        binding.i18n = i18n

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
                emailField.error = i18n.emailEmptyError
                emailField.requestFocus()
            }

            if (password.isEmpty()) {
                passwordField.error = i18n.passwordEmptyError
                passwordField.requestFocus()
            }

            if (password != passwordConfirm) {
                passwordConfirmField.error = i18n.passwordDontMatchError
                passwordConfirmField.requestFocus()
            }

            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm == password) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(
                                baseContext, i18n.authSuccess,
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                baseContext, i18n.authFailed,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }
        }

    }
}