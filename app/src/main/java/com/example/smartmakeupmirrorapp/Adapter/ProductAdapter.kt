package com.example.smartmakeupmirrorapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartmakeupmirrorapp.Models.Product
import com.example.smartmakeupmirrorapp.R

class ProductAdapter(private val products: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {
    var onItemClick : ((Product)->Unit)?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName : TextView = itemView.findViewById(R.id.product_name)
        private val productDes : TextView = itemView.findViewById(R.id.product_description)
        private val productPrice : TextView = itemView.findViewById(R.id.product_price)
        val image : ImageView =itemView.findViewById(R.id.imageView)

        fun bind(product: Product) {
            productName.text = product.name
            productDes.text = product.description
            productPrice.text = product.price.toString()
            image.load("http://192.168.1.6:9090/img/"+product.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product, parent, false)
        return ProductAdapter.ViewHolder(view)
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