package com.alex.gardenia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.alex.gardenia.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUserName = findViewById<EditText>(R.id.user_name)
        val editTextPassword = findViewById<EditText>(R.id.user_paswd)
        val loginButton :Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            println(editTextUserName.text)
            println(editTextPassword.text)
        }


    }
}