package com.example.smartmakeupmirrorapp

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.smartmakeupmirrorapp.Retrofit.SharedPrefManager

class SettingsActivity : AppCompatActivity() {
    private lateinit var nametxt: TextView
    private lateinit var emailtxt: TextView
    private lateinit var logoutB: ImageView
    private lateinit var edit: AppCompatButton
    private lateinit var back: ImageView
    private lateinit var home: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        logoutB = findViewById(R.id.logoutt)
        edit = findViewById(R.id.edit)
        back = findViewById(R.id.backBtn)
        home = findViewById(R.id.imageView3)
        back.setOnClickListener{
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }
        home.setOnClickListener{
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }


        edit.setOnClickListener{
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val intent = Intent(applicationContext, ProfileActivity::class.java)

            startActivity(intent)
        }

        logoutB.setOnClickListener{
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            // Get an instance of SharedPreferences.Editor
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
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