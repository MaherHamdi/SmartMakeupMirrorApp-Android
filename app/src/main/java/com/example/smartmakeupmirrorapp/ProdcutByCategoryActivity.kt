package com.example.smartmakeupmirrorapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import com.example.smartmakeupmirrorapp.Adapter.ProductAdapter
import com.example.smartmakeupmirrorapp.Adapter.SubCategoryAdapter
import com.example.smartmakeupmirrorapp.R.id.category
import com.example.smartmakeupmirrorapp.Models.*
import com.example.smartmakeupmirrorapp.R.id.ProductDetailimg

import com.example.smartmakeupmirrorapp.Retrofit.SubCategoryRepo

class ProdcutByCategoryActivity : AppCompatActivity() {
    private lateinit var recyclerViewSubCategory: RecyclerView
    private lateinit var subCategorys: ArrayList<SubCategory>
    private lateinit var subCategoryAdapter: SubCategoryAdapter
    private lateinit var recyclerViewProduct: RecyclerView
    private lateinit var products: ArrayList<Product>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryLayout:ConstraintLayout
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_bycategory)
        val category = intent.getParcelableExtra<Category>("category")
        //val categoryLayout: ConstraintLayout = findViewById(R.id.category)

        val TAGSubCategory = "SubCategoryList"
        val categoryforSubCategory = SubCategoryByCategory(category!!._id)
        val textView: TextView = findViewById(R.id.product_name_by_category)
        val items_Nb: TextView = findViewById(R.id.product_item_number)
        back = findViewById(R.id.backBtn)

        back.setOnClickListener {
            startActivity(Intent(applicationContext, AcceuilActivity::class.java))
        }


        textView.text = category!!.name
        SubCategoryRepo.apiService.getSubCategoryByCategorys(categoryforSubCategory)
            .enqueue(object : Callback<List<SubCategory>> {
                override fun onResponse(
                    call: Call<List<SubCategory>>,
                    response: Response<List<SubCategory>>
                ) {
                    if (response.isSuccessful) {
                        recyclerViewSubCategory = findViewById(R.id.recyclerViewSubCategory)

                        recyclerViewSubCategory.layoutManager =
                            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

                        val subCategorysList = response.body()
                        subCategorysList?.let {
                            subCategoryAdapter = SubCategoryAdapter(subCategorysList)
                            recyclerViewSubCategory.adapter = subCategoryAdapter
                            subCategoryAdapter.onItemClick = {

                                    subCategory ->
                                myFunction(subCategory)
                                //     subCategoryId ->

                                //  getProductBySubCategory(category!!._id)
                                //    val subCategory = intent.getParcelableExtra<SubCategory>("subCategory")
                                //   if (subCategory != null) {
                                //   val TAG = "ProductList"
                                //    Log.e(TAG, "Response successful: $subCategory")



                            }


                        }

                        //Log.e(TAGSubCategory, "Response  successful. : ${subCategorysList}")

                    } else {
                        Log.e(
                            TAGSubCategory,
                            "Response not successful. Status code: ${response.code()}"
                        )
                    }


                }

                override fun onFailure(call: Call<List<SubCategory>>, t: Throwable) {
                    Log.e(TAGSubCategory, "Network request failed", t)
                }
            })


        getProductBySubCategory(category!!._id)


    }


    /////////////////////////prduct//////////////////////////////
    fun myFunction(subCategory:SubCategory){
        val items_Nb: TextView = findViewById(R.id.product_item_number)

        //   val TAG = "ProductList"
        //   Log.e(TAG, "Response  successful. : ${subCategory}")
        val TAG = "ProductList"

        val spaceHeight = resources.getDimensionPixelSize(R.dimen.right)
        val itemDecoration = CustomItemDecoration(spaceHeight)

        val subCategoryForProduct = ProductsBySubCategory(subCategory!!._id)
        ProductRepo.apiService.getProductBySubCategory(subCategoryForProduct).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    recyclerViewProduct=findViewById(R.id.recyclerViewProduct)
                    recyclerViewProduct?.layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)

                    val productsList = response.body()
                    items_Nb.text= productsList!!.size.toString()
                    productsList?.let{
                        productAdapter = ProductAdapter(productsList)
                        recyclerViewProduct.adapter=productAdapter
                        //recyclerViewProduct.addItemDecoration(itemDecoration)
                        productAdapter.onItemClick={
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
    fun getProductBySubCategory(id:String){
        val items_Nb: TextView = findViewById(R.id.product_item_number)

        //categoryLayout= findViewById(R.id.category)
        //categoryLayout.setBackgroundColor(Color.parseColor("#FF0000"))
        val spaceHeight = resources.getDimensionPixelSize(R.dimen.right)
        val itemDecoration = CustomItemDecoration(spaceHeight)
        val TAG = "ProductList"
        val categoryForProduct = ProductsByCategory(id)
        ProductRepo.apiService.getProductByCategory(categoryForProduct).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    recyclerViewProduct=findViewById(R.id.recyclerViewProduct)
                    recyclerViewProduct?.layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)

                    val productsList = response.body()
                    items_Nb.text= productsList!!.size.toString()
                    productsList?.let{
                        productAdapter = ProductAdapter(productsList)
                        recyclerViewProduct.adapter=productAdapter
                        recyclerViewProduct.addItemDecoration(itemDecoration)
                        productAdapter.onItemClick={
                            val intent = Intent(applicationContext, ProdcutDetailActivity::class.java)
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