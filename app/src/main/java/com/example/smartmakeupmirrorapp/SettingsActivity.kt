package com.example.smartmakeupmirrorapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smartmakeupmirrorapp.Retrofit.SharedPrefManager

class SettingsActivity : AppCompatActivity() {
    private lateinit var nametxt: TextView
    private lateinit var emailtxt: TextView
    private lateinit var logoutB: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        logoutB = findViewById(R.id.logoutt)

        logoutB.setOnClickListener{
            SharedPrefManager(applicationContext).removeUser()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val intent = Intent(applicationContext, LoginActivity::class.java)

            startActivity(intent)
        }
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        nametxt = findViewById(R.id.name)
        emailtxt = findViewById(R.id.email)

        nametxt.text= name
        emailtxt.text = email


    }
}