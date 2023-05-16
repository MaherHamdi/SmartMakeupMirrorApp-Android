package tn.farah.smartmakeupapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import tn.farah.smartmakeupapp.data.viewModel.ProductViewModel

class ListProduct : AppCompatActivity() {
 private val productViewModel:ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)
        productViewModel.products.observe(this, Observer {
            //UpdateUI With new ListOf Product
        })
    }
}