package tn.farah.smartmakeupapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox

class favorite_item : AppCompatActivity() {
    private lateinit var  checkBoxFav: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_item)
        checkBoxFav = findViewById(R.id.checkBoxFav)

    }
}