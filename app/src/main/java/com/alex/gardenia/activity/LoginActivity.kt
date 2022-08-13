package com.alex.gardenia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alex.gardenia.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val singUpLabel = findViewById<TextView>(R.id.sing_up_inner)
        singUpLabel.setOnClickListener{
            val singUpIntent = Intent(LoginActivity@this,SignupActivity::class.java)
            startActivity(singUpIntent)
        }
    }
}