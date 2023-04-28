package com.example.smartmakeupmirrorapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartmakeupmirrorapp.Models.CartItem
import com.example.smartmakeupmirrorapp.R
import com.example.smartmakeupmirrorapp.ShoppingCart
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>,val totalPriceTextView: TextView) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ShoppingCartAdapter.ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ShoppingCartAdapter.ViewHolder, position: Int) {
        viewHolder.bindItem(cartItems[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = itemView.findViewById(R.id.product_name)
        private val textView2: TextView = itemView.findViewById(R.id.product_price)
        private val textView3: TextView = itemView.findViewById(R.id.product_quantity)
        private val imageView: ImageView = itemView.findViewById(R.id.product_image)
        private val minusCart: ImageButton = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn)
        private val plusCart: ImageButton = itemView.findViewById(R.id.eachCartItemAddQuantityBtn)
        private val deleteCart: ImageView = itemView.findViewById(R.id.eachCartItemDeleteBtn)

        fun bindItem(cartItem: CartItem) {

            imageView.load("http://192.168.1.6:9090/img/"+cartItem.product.image)
            textView.text = cartItem.product.name
            //textView2.text = "$${cartItem.product.price}"
            textView3.text = cartItem.quantity.toString()
            var totalPrice = cartItem.product.price * cartItem.quantity


            textView2.text = "$${totalPrice}"

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> { emitter ->
                plusCart.setOnClickListener {
                    val item = CartItem(cartItem.product)
                    ShoppingCart.addItem(item)
                    emitter.onNext(ShoppingCart.getCart())
                }

                minusCart.setOnClickListener {
                    val item = CartItem(cartItem.product)
                    ShoppingCart.removeItem(item, itemView.context)
                    emitter.onNext(ShoppingCart.getCart())
                }

                deleteCart.setOnClickListener {
                    val item = CartItem(cartItem.product)
                    ShoppingCart.deleteItem(item, itemView.context)
                    emitter.onNext(ShoppingCart.getCart())
                }

            }).subscribe { cart ->
                var quantity = 0
                var totalPricee = 0.0

                cart.forEach { cartItem ->
                    quantity += cartItem.quantity
                    totalPricee += cartItem.product.price * cartItem.quantity

                }

                //textView3.text = quantity.toString()

                cartItems = cart // update the list of cart items
                totalPriceTextView.text = "$${totalPricee}"
                notifyDataSetChanged()
            }
        }
    }

}

