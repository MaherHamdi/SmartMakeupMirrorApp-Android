package com.example.smartmakeupmirrorapp

import ai.deepar.deepar_example.MainActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SettingsActivity : AppCompatActivity() {
    private lateinit var nametxt: TextView
    private lateinit var emailtxt: TextView
    private lateinit var logoutB: ImageView
    private lateinit var edit: AppCompatButton
    private lateinit var back: ImageView
    private lateinit var home: ImageView
    private lateinit var cartView: ImageView
    private lateinit var cartSize: TextView
    private lateinit var fav: ImageView
    private lateinit var pwdpage: ImageView
    private var isDarkThemeEnabled = false
    private var currentTheme: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonOpenCam = findViewById<FloatingActionButton>(R.id.video)
        buttonOpenCam.setOnClickListener {

            //     var intent = Intent(this, Camera::class.java)

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        currentTheme = AppCompatDelegate.getDefaultNightMode()

        val switchDarkTheme = findViewById<Switch>(R.id.darktheme)
        switchDarkTheme.isChecked = currentTheme == AppCompatDelegate.MODE_NIGHT_YES
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            val newTheme =
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            if (currentTheme != newTheme) {
                AppCompatDelegate.setDefaultNightMode(newTheme)
                recreate()
            }
        }

        logoutB = findViewById(R.id.logoutt)
        edit = findViewById(R.id.edit)
        back = findViewById(R.id.backBtn)
        home = findViewById(R.id.imageView3)
        cartView = findViewById(R.id.imageViewhom)
        fav = findViewById(R.id.imageViewfav)
        pwdpage = findViewById(R.id.changepw)

        pwdpage.setOnClickListener {
            UpdatePasswordFragment().show(supportFragmentManager, "MyCustomFragment")
        }
        //cartSize =  findViewById(R.id.cart_size)
        fav.setOnClickListener {
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        }
        cartView.setOnClickListener {
            startActivity(Intent(applicationContext, ShoppingCartActivity::class.java))
        }
        back.setOnClickListener {
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }
        home.setOnClickListener {
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }


        edit.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val intent = Intent(applicationContext, ProfileActivity::class.java)

            startActivity(intent)
        }

        logoutB.setOnClickListener {
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

        nametxt.text = name
        emailtxt.text = email


    }

    override fun onResume() {
        super.onResume()
        val currentTheme = AppCompatDelegate.getDefaultNightMode()
        val switchDarkTheme = findViewById<Switch>(R.id.darktheme)
        switchDarkTheme.isChecked = currentTheme == AppCompatDelegate.MODE_NIGHT_YES

    }
}