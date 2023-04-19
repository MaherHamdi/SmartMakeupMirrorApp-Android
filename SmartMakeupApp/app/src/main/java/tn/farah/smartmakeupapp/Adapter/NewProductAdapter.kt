package tn.farah.smartmakeupapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import tn.farah.smartmakeupapp.R
import tn.farah.smartmakeupapp.data.models.Product

class NewProductAdapter (private val new_products: List<Product>): RecyclerView.Adapter<NewProductAdapter.ViewHolder>()  {
var onItemClick : ((Product)->Unit)?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName : TextView = itemView.findViewById(R.id.product_name)
        private val productDes : TextView = itemView.findViewById(R.id.product_description)
        private val productPrice : TextView = itemView.findViewById(R.id.product_price)
private val image :ImageView = itemView.findViewById(R.id.imageView_new_Product)

        fun bind(product: Product) {
            productName.text = product.name
            productDes.text = product.description
            productPrice.text = product.price.toString()
            image.load("http://192.168.1.103:9090/img/"+product.image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_new, parent, false)
        return NewProductAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = new_products[position]
        holder.bind(product)

    holder.itemView.setOnClickListener {
        onItemClick?.invoke(product)
    }
    }

    override fun getItemCount(): Int {
        return new_products.size
    }
}