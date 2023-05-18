package com.example.smartmakeupmirrorapp




import ProductRepo
import ai.deepar.deepar_example.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartmakeupmirrorapp.Adapter.CategoryAdapter
import com.example.smartmakeupmirrorapp.Adapter.ProductAdapter
import com.example.smartmakeupmirrorapp.Models.Category
import com.example.smartmakeupmirrorapp.Models.Product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AcceuilActivity : AppCompatActivity() {
    private lateinit var nametxt: TextView
    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var recyclerViewProduct: RecyclerView
    private lateinit var categorys: ArrayList<Category>
    private lateinit var product: ArrayList<Product>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var newProductAdapter: ProductAdapter
    private lateinit var profile: ImageView
    private lateinit var cartView: ImageView
    private lateinit var  cartSize : TextView
    private lateinit var  fav : ImageView
    private lateinit var profilee : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)
        Paper.init(this)
        val buttonOpenCam = findViewById<FloatingActionButton>(R.id.video)
        buttonOpenCam.setOnClickListener {

            //     var intent = Intent(this, Camera::class.java)

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}
        profilee = findViewById(R.id.prof)
        profilee.setOnClickListener {
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
      //  recyclerViewCategories = findViewById(R.id.recyclerViewCategory)
       // recyclerViewProduct = findViewById(R.id.recyclerViewProduct)
       // linearLayoutManager = LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false)
        //recyclerViewCategories.layoutManager = linearLayoutManager
        profile = findViewById(R.id.imageViewuser)
        cartView = findViewById(R.id.imageViewhom)
        fav = findViewById(R.id.imageViewfav)
        //cartSize =  findViewById(R.id.cart_size)
        fav.setOnClickListener {
            startActivity(Intent(applicationContext, FavoriteActivity::class.java))
        }
        cartView.setOnClickListener {
            startActivity(Intent(applicationContext, ShoppingCartActivity::class.java))
        }
        profile.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        nametxt = findViewById(R.id.textView)


        nametxt.text= "Hi $name"
        //cartSize.text = ShoppingCart.getShoppingCartSize().toString()


        val TAG1 = "Category"
        CategoryRepo.apiService.getGategorys().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    recyclerViewCategories = findViewById(R.id.recyclerViewCategory)

                    recyclerViewCategories?.layoutManager =
                        LinearLayoutManager(applicationContext,  LinearLayoutManager.HORIZONTAL, false)

                    val categorysList = response.body()
                    categorysList?.let {
                        categoryAdapter = CategoryAdapter(categorysList)
                        recyclerViewCategories.adapter = categoryAdapter

                        categoryAdapter.onItemClick = {
                            val intent =
                                Intent(applicationContext, ProdcutByCategoryActivity::class.java)
                            intent.putExtra("category", it)
                            startActivity(intent)
                        }
                    }

                    Log.e(TAG1, "Response  successful. : ${categorysList}")

                } else {
                    Log.e(TAG1, "Response not successful. Status code: ${response.code()}")
                }


            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e(TAG1, "Network request failed", t)
            }
        })


        val TAG = "ProductListFragment"
       /** CategoryRepo.apiService.getCategory().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    recyclerViewCategories=findViewById(R.id.recyclerViewCategory)
                    recyclerViewCategories?.layoutManager = LinearLayoutManager(applicationContext,  LinearLayoutManager.HORIZONTAL, false)

                    val categoryList = response.body()
                    categoryList?.let{
                        categoryAdapter = CategoryAdapter(categoryList)
                        recyclerViewCategories.adapter=categoryAdapter

                        categoryAdapter.onItemClick={
                            val intent = Intent(applicationContext, ProdcutByCategoryActivity::class.java)
                            intent.putExtra("category", it)
                            startActivity(intent)
                        }
                    }

                    Log.e(TAG, "Response  successful. : ${categoryList}")

                } else {
                    Log.e(TAG, "Response not successful. Status code: ${response.code()}")
                }


            }


            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e(TAG, "Network request failed", t)
            }
        })

    **/

        ProductRepo.apiService.getProducts().enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    recyclerViewProduct=findViewById(R.id.recyclerViewProduct)
                    recyclerViewProduct?.layoutManager = LinearLayoutManager(applicationContext,  LinearLayoutManager.HORIZONTAL, false)

                    val productsList = response.body()
                    productsList?.let{
                        newProductAdapter = ProductAdapter(productsList)
                        recyclerViewProduct.adapter=newProductAdapter

                        newProductAdapter.onItemClick={
                            val intent = Intent(applicationContext,ProdcutDetailActivity::class.java)
                            intent.putExtra("product",it)
                            startActivity(intent)
                        }
                    }

                    Log.e(TAG, "Response  successful. : ${productsList}")

                } else {
                    Log.e(TAG, "Response not successful. Status code: ${response.code()}")
                }


            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e(TAG, "Network request failed", t)
            }
        })




    }

}