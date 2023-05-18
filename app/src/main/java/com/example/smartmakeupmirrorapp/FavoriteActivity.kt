package com.example.smartmakeupmirrorapp

import ai.deepar.deepar_example.MainActivity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmakeupmirrorapp.Adapter.FavoriteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavoriteActivity : AppCompatActivity() {

    lateinit var adapter: FavoriteAdapter
    private lateinit var recyclerViewCart: RecyclerView
     private lateinit var profile: ImageView
     private lateinit var cartView: ImageView
    private lateinit var  cartSize : TextView
    private lateinit var  fav : ImageView
    private lateinit var home: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val buttonOpenCam = findViewById<FloatingActionButton>(R.id.video)
        buttonOpenCam.setOnClickListener {

            //     var intent = Intent(this, Camera::class.java)

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}
        profile = findViewById(R.id.imageViewuser)
        cartView = findViewById(R.id.imageViewhom)
        home = findViewById(R.id.imageView3)

        cartView.setOnClickListener {
            startActivity(Intent(applicationContext, ShoppingCartActivity::class.java))
        }
        profile.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        home.setOnClickListener{
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }
        recyclerViewCart = findViewById(R.id.favorite_recyclerView)

        adapter = FavoriteAdapter(this, Favorite.getFavorite())

        recyclerViewCart.layoutManager = LinearLayoutManager(this)
        recyclerViewCart.adapter = adapter






    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()

            }
        }

        return super.onOptionsItemSelected(item!!)
    }


}

