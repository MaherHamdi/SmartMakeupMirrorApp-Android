package tn.farah.smartmakeupapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import tn.farah.smartmakeupapp.R
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class ProductCamAdapter (private val products: List<Product>): RecyclerView.Adapter<ProductCamAdapter.ViewHolder>()  {
    var onItemClick : ((Product)->Unit)?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

     private   val image : ImageView =itemView.findViewById(R.id.imageView_cam_Product)

        fun bind(product: Product) {

            image.load(ProductRepo.BASE_URL+"img/"+product.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_cam, parent, false)
        return ProductCamAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}