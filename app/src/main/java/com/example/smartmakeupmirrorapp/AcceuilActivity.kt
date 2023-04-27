package com.example.smartmakeupmirrorapp



import ProductRepo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmakeupmirrorapp.Adapter.CategoryAdapter
import com.example.smartmakeupmirrorapp.Adapter.ProductAdapter
import com.example.smartmakeupmirrorapp.Models.Category
import com.example.smartmakeupmirrorapp.Models.Product
import io.paperdb.Paper
import retrofit2.Call
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)
        Paper.init(this)
      //  recyclerViewCategories = findViewById(R.id.recyclerViewCategory)
       // recyclerViewProduct = findViewById(R.id.recyclerViewProduct)
       // linearLayoutManager = LinearLayoutManager(this,  LinearLayoutManager.HORIZONTAL, false)
        //recyclerViewCategories.layoutManager = linearLayoutManager
        profile = findViewById(R.id.imageViewuser)
        cartView = findViewById(R.id.imageViewhom)
        //cartSize =  findViewById(R.id.cart_size)
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

        recyclerViewCategories = findViewById(R.id.recyclerViewCategory)

        recyclerViewCategories.layoutManager =
            LinearLayoutManager(applicationContext,  LinearLayoutManager.HORIZONTAL, false)

        categorys = ArrayList()
        categorys.add(Category("1", "Vegan"))
        categorys.add(Category("2", "Face"))
        categorys.add(Category("3", "Eye"))
        categorys.add(Category("4", "Lip"))
        categorys.add(Category("5", "cate4"))

        categoryAdapter = CategoryAdapter(categorys)
        recyclerViewCategories.adapter = categoryAdapter
        categoryAdapter.onItemClick = {
            val intent = Intent(this, ProdcutByCategoryActivity::class.java)
            intent.putExtra("category", it)
            startActivity(intent)
        }


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