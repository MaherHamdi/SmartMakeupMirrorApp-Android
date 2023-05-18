package com.example.smartmakeupmirrorapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.smartmakeupmirrorapp.Models.CartItem
import com.example.smartmakeupmirrorapp.Models.Product

class ProdcutDetailActivity : AppCompatActivity() {
    private lateinit var plusBtn: ImageView
    private lateinit var minusBtn: ImageView
    private lateinit var addCart: TextView
    private lateinit var numberOrder: TextView
    var numberOr: Int = 1
    private lateinit var cartItem: CartItem
    private lateinit var tuto : Button
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)
        tuto = findViewById(R.id.button2)
        back = findViewById(R.id.backBtn)
        tuto.setOnClickListener {
            startActivity(Intent(applicationContext, ListVideoActivity::class.java))
        }
        back.setOnClickListener {
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }




        val product = intent.getParcelableExtra<Product>("product")
        if (product != null) {
            var nameProduct: TextView = findViewById(R.id.ProductNameee)
            var desProduct: TextView = findViewById(R.id.textView13)
            var prixProduct: TextView = findViewById(R.id.ProductPrice)
            var image : ImageView =findViewById(R.id.ProductDetailimg)

            nameProduct.text = product.name
            desProduct.text = product.description
            prixProduct.text = product.price.toString() + "DT"
            image.load("http://192.168.1.9:9090/img/"+product.image)

        }


    }
}