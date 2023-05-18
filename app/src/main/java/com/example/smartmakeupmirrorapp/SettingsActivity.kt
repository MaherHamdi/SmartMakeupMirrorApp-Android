package com.example.smartmakeupmirrorapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class SettingsActivity : AppCompatActivity() {
    private lateinit var nametxt: TextView
    private lateinit var emailtxt: TextView
    private lateinit var logoutB: ImageView
    private lateinit var edit: AppCompatButton
    private lateinit var back: ImageView
    private lateinit var home: ImageView
    private lateinit var cartView: ImageView
    private lateinit var  cartSize : TextView
    private lateinit var  fav : ImageView
    private lateinit var pwdpage : ImageView
    private var isDarkThemeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isDarkThemeEnabled) {
            setTheme(R.style.Theme_SmartMakeupMirrorApp_Dark)
        } else {
            setTheme(R.style.Theme_SmartMakeupMirrorApp)
        }
        setContentView(R.layout.activity_settings)
        val switchDarkTheme = findViewById<Switch>(R.id.darktheme)
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            isDarkThemeEnabled = isChecked
            recreate() // Recreate the activity to apply the new theme
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