package com.example.smartmakeupmirrorapp

import android.annotation.SuppressLint
import android.content.Context
import com.example.smartmakeupmirrorapp.Models.CartItem
import com.example.smartmakeupmirrorapp.Models.FavoriteItem
import io.paperdb.Paper


class Favorite {


    companion object {


        fun addItem(favoriteItem: FavoriteItem) {
            val favorite = Favorite.getFavorite()

            val targetItem = favorite.singleOrNull { it.product._id == favoriteItem.product._id }

            if (targetItem == null) {

                favorite.add(favoriteItem)

            }

            Favorite.saveFavorite(favorite)


        }

        fun removeItem(favItem: FavoriteItem, context: Context) {

            val cart = Favorite.getFavorite()


            val targetItem = cart.singleOrNull { it.product._id == favItem.product._id }

            if (targetItem != null) {


                    cart.remove(targetItem)




            }


            Favorite.saveFavorite(cart)

        }
        fun deleteItem(cartItem: CartItem, context: Context) {

            val cart = ShoppingCart.getCart()


            val targetItem = cart.singleOrNull { it.product._id == cartItem.product._id }

            if (targetItem != null) {

                cart.remove(targetItem)



            }

            ShoppingCart.saveCart(cart)

        }
        @SuppressLint("SuspiciousIndentation")
        fun deleteCheckout() {

            val cart = ShoppingCart.getCart()

            cart.clear()
            ShoppingCart.saveCart(cart)

        }

        fun saveFavorite(favorite: MutableList<FavoriteItem>) {
            Paper.book().write("fav", favorite)
        }

        fun getFavorite(): MutableList<FavoriteItem> {
            return Paper.book().read("fav", mutableListOf())!!
        }

        fun getShoppingCartSize(): Int {

            var cartSize = 0
            ShoppingCart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}
