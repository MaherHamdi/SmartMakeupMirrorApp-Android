package tn.farah.smartmakeupapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import coil.load
import tn.farah.smartmakeupapp.data.models.Product

class product_detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
  val product = intent.getParcelableExtra<Product>("product")
if(product !=null){
  var  nameProduct : TextView=findViewById(R.id.product_name_detail)
    var  desProduct : TextView=findViewById(R.id.product_des_detail)
var prixProduct : TextView=findViewById(R.id.product_prix_detail)
    var image : ImageView=findViewById(R.id.product_image_detail)

    nameProduct.text=product.name
    desProduct.text=product.description
    prixProduct.text=product.price.toString()
    image.load("http://192.168.1.103:9090/img/"+product.image)

}
    }
}