package com.example.smartmakeupmirrorapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmakeupmirrorapp.Adapter.ShoppingCartAdapter
import com.example.smartmakeupmirrorapp.Models.CartItem
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult


class ShoppingCartActivity : AppCompatActivity() {

    lateinit var adapter: ShoppingCartAdapter
    private lateinit var recyclerViewCart: RecyclerView
    private lateinit var total_price: TextView
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String
    private lateinit var checkout: Button
    private lateinit var cartItems: List<CartItem>
    private lateinit var back: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        setContentView(R.layout.activity_shopping_cart)
        recyclerViewCart = findViewById(R.id.shopping_cart_recyclerView)
        total_price = findViewById(R.id.total_price)
        checkout = findViewById(R.id.checkout)
        back = findViewById(R.id.backBtn)
        back.setOnClickListener{
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }


        checkout.setOnClickListener {
            val totalPrice = ShoppingCart.getCart().fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }
            val amount = (totalPrice * 100).toLong()
           // val requestBody = mapOf("amount" to amount)
            "http://192.168.1.135:9090/payment-sheet/${amount}".httpPost().responseJson { _, _, result ->
                if (result is Result.Success) {
                    print (amount);
                    val responseJson = result.get().obj()
                    paymentIntentClientSecret = responseJson.getString("paymentIntent")
                    customerConfig = PaymentSheet.CustomerConfiguration(
                        responseJson.getString("customer"),
                        responseJson.getString("ephemeralKey")
                    )
                    val publishableKey = responseJson.getString("publishableKey")
                    PaymentConfiguration.init(this, publishableKey)
                   // val totalPrice = ShoppingCart.getCart().fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }
                   // val amount = (totalPrice * 100).toLong()
                    presentPaymentSheet()
                }
            }
        }

        adapter = ShoppingCartAdapter(this, ShoppingCart.getCart(), total_price)

        recyclerViewCart.layoutManager = LinearLayoutManager(this)
        recyclerViewCart.adapter = adapter

        cartItems = ShoppingCart.getCart()


        var totalPrice = ShoppingCart.getCart().fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }
        total_price.text = "$${totalPrice}"

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()

            }
        }

        return super.onOptionsItemSelected(item!!)
    }

    fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,

            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business
                // can handle payment methods that complete payment after a delay, like SEPA Debit and Sofort.
                allowsDelayedPaymentMethods = true,




            )
        )
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen

                ShoppingCart.deleteCheckout()
                ShoppingCart.getCart()
                adapter.notifyDataSetChanged()
                print("Completed")
            }
        }


    }
}

