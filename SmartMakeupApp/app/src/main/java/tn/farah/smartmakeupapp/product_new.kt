package tn.farah.smartmakeupapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox

class product_new : AppCompatActivity() {
    private lateinit var  checkBoxFav : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_new)
        val checkBoxFav =findViewById<CheckBox>(R.id.checkBoxFav)

    }
}