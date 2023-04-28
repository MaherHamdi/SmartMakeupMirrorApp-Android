package com.example.smartmakeupmirrorapp.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartmakeupmirrorapp.Favorite
import com.example.smartmakeupmirrorapp.Models.CartItem
import com.example.smartmakeupmirrorapp.Models.FavoriteItem
import com.example.smartmakeupmirrorapp.Models.Product
import com.example.smartmakeupmirrorapp.R
import com.example.smartmakeupmirrorapp.ShoppingCart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe


class ProductAdapter(private val products: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {
    var onItemClick : ((Product)->Unit)?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName : TextView = itemView.findViewById(R.id.product_name)
        private val productDes : TextView = itemView.findViewById(R.id.product_description)
        private val productPrice : TextView = itemView.findViewById(R.id.product_price)
        val image : ImageView =itemView.findViewById(R.id.imageView)
        val addToCart : FloatingActionButton =itemView.findViewById(R.id.addProduct)
        //val cartSize : TextView = itemView.findViewById(R.id.cart_size)
        private val addFav: CheckBox = itemView.findViewById(R.id.checkBoxFav)


        fun bind(product: Product) {
            productName.text = product.name
            productDes.text = product.description
            productPrice.text = product.price.toString()
            image.load("http://192.168.1.6:9090/img/"+product.image)
            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> { emitter ->

                addToCart.setOnClickListener {

                    val item = CartItem(product)

                    ShoppingCart.addItem(item)
                    //notify users


                    emitter.onNext(ShoppingCart.getCart())

                }




            }).subscribe { cart ->

                var quantity = 0

                cart.forEach { cartItem ->

                    quantity += cartItem.quantity
                }

                //(itemView.context as AcceuilActivity).cartSize.text = quantity.toString()
                //cartSize.text = quantity.toString()
               // Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()

//            }
//                Toast.makeText(itemView.context, "${product.name} added to your cart", Toast.LENGTH_SHORT).show()


            }
            Observable.create(ObservableOnSubscribe<MutableList<FavoriteItem>> { emitter ->
                addFav.setOnCheckedChangeListener { checkBox, isChecked ->
                    if (isChecked) {

                        val item = FavoriteItem(product)
                        Favorite.addItem(item)
                        emitter.onNext(Favorite.getFavorite())

                    }
                }



            }).subscribe { cart ->

                //textView3.text = quantity.toString()

            // update the list of cart items

            }



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