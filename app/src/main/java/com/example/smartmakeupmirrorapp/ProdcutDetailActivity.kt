package com.example.smartmakeupmirrorapp

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        addCart = findViewById(R.id.addCart)
        plusBtn = findViewById(R.id.plus)
        minusBtn = findViewById(R.id.minus)
        numberOrder = findViewById(R.id.numberOrder)
        numberOrder.text = numberOr.toString()
        plusBtn.setOnClickListener {
            numberOr = numberOr + 1
            numberOrder.text = numberOr.toString()

        }
        minusBtn.setOnClickListener {
            if (numberOr > 1) {
                numberOr = numberOr - 1
            }
            numberOrder.text = numberOr.toString()
        }


        val product = intent.getParcelableExtra<Product>("product")
        if (product != null) {
            var nameProduct: TextView = findViewById(R.id.titleText)
            var desProduct: TextView = findViewById(R.id.descriptiontxt)
            var prixProduct: TextView = findViewById(R.id.pricetxt)
            var image : ImageView =findViewById(R.id.imageView6)

            nameProduct.text = product.name
            desProduct.text = product.description
            prixProduct.text = product.price.toString() + "DT"
            image.load("http://192.168.1.6:9090/img/"+product.image)

        }


    }
}