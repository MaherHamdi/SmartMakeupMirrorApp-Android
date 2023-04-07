package com.example.smartmakeupmirrorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.smartmakeupmirrorapp.Retrofit.SharedPrefManager

class MainActivity : AppCompatActivity() {
    private lateinit var logoutt: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logoutt= findViewById(R.id.cancel)
        logoutt.setOnClickListener {
            SharedPrefManager(applicationContext).removeUser()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val intent = Intent(applicationContext, LoginActivity::class.java)

            startActivity(intent)
        }
    }
}