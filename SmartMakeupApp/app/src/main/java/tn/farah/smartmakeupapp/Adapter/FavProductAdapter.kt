package tn.farah.smartmakeupapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.farah.smartmakeupapp.R
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.models.ProductsIsFaved
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class FavProductAdapter  (private val fav_products: List<Product>): RecyclerView.Adapter<FavProductAdapter.ViewHolder>()  {
    var onItemClick : ((Product)->Unit)?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName : TextView = itemView.findViewById(R.id.product_name_fav)
        private val productPrice : TextView = itemView.findViewById(R.id.product_price_fav)
        private val image : ImageView = itemView.findViewById(R.id.imageView_product_fav)
        private val checkBoxFav : CheckBox = itemView.findViewById(R.id.checkBoxFav)
        val TAG = "testCheked"
        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = product.price.toString()
            image.load(ProductRepo.BASE_URL+"img/"+product.image)


            checkBoxFav.setChecked(true)
            if(product.isFaved==true){
                checkBoxFav.setChecked(true)

            }else{
                checkBoxFav.setChecked(false)

            }
            checkBoxFav. setOnCheckedChangeListener { _, isChecked ->
                // Code à exécuter lorsque l'état du CheckBox est modifié
                if (isChecked) {
                    val productsIsFaved= ProductsIsFaved(true)
                    ProductRepo.apiService.updateFavedProduct(product._id,productsIsFaved).enqueue(object :
                        Callback<Product> {
                        override fun onResponse(
                            call: Call<Product>,
                            response: Response<Product>
                        ) {
                            Log.e(TAG, "yesssssssss "+product)
                            checkBoxFav.setChecked(true)

                        }
                        override fun onFailure(call: Call<Product>, t: Throwable) {
                            Log.e(TAG, "Network request failed", t)
                        }
                    })

                    // Le CheckBox est coché
                    // Ajouter votre code ici
                } else {
                    val productsIsFaved= ProductsIsFaved(false)
                    ProductRepo.apiService.updateFavedProduct(product._id,productsIsFaved).enqueue(object :
                        Callback<Product> {
                        override fun onResponse(
                            call: Call<Product>,
                            response: Response<Product>
                        ) {
                            Log.e(TAG, "noooooooooo : "+product)
                        }
                        override fun onFailure(call: Call<Product>, t: Throwable) {
                            Log.e(TAG, "Network request failed", t)
                        }
                    })

                    // Le CheckBox est décoché
                    // Ajouter votre code ici
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_favorite_item, parent, false)
        return FavProductAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = fav_products[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return fav_products.size
    }

}