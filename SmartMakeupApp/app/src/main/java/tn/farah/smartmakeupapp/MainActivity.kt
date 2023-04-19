package tn.farah.smartmakeupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Récupération de la référence du bouton dans le layout
        val button = findViewById<Button>(R.id.startBtn)

        // Ajout d'un listener sur le bouton pour gérer le clic
        button.setOnClickListener {
           val intent = Intent(this, AcceuilActivity::class.java)
            print("OK!!")
         //     val intent = Intent(this, MainActivity2::class.java)

            startActivity(intent)
        }
    }


}