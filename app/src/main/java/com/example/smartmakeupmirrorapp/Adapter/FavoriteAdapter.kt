package com.example.smartmakeupmirrorapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartmakeupmirrorapp.Favorite
import com.example.smartmakeupmirrorapp.Models.FavoriteItem
import com.example.smartmakeupmirrorapp.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

class FavoriteAdapter(var context: Context, var cartItems: List<FavoriteItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoriteAdapter.ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.favorite_list_item, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: FavoriteAdapter.ViewHolder, position: Int) {
        viewHolder.bindItem(cartItems[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = itemView.findViewById(R.id.product_name)
        private val imageView: ImageView = itemView.findViewById(R.id.product_image)
        private val textView2: TextView = itemView.findViewById(R.id.product_price)

        private val addFav: CheckBox = itemView.findViewById(R.id.checkBoxFav)


        fun bindItem(cartItem: FavoriteItem) {

            imageView.load("http://192.168.1.6:9090/img/"+cartItem.product.image)
            textView.text = cartItem.product.name
            textView2.text = "$${cartItem.product.price}"





            Observable.create(ObservableOnSubscribe<MutableList<FavoriteItem>> { emitter ->
                addFav.setOnCheckedChangeListener { checkBox, isChecked ->
                    if (isChecked) {
                        val item = FavoriteItem(cartItem.product)
                        Favorite.removeItem(item,itemView.context)
                        emitter.onNext(Favorite.getFavorite())
                    } else {

                    }

                }




            }).subscribe { cart ->

                //textView3.text = quantity.toString()

                cartItems = cart // update the list of cart items
                notifyDataSetChanged()
            }
        }
    }

}

