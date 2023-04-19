package com.example.smartmakeupmirrorapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartmakeupmirrorapp.Adapter.ProductAdapter
import com.example.smartmakeupmirrorapp.Adapter.SubCategoryAdapter
import com.example.smartmakeupmirrorapp.Models.Category
import com.example.smartmakeupmirrorapp.Models.Product
import com.example.smartmakeupmirrorapp.Models.SubCategory
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class ProdcutByCategoryActivity : AppCompatActivity() {
    private lateinit var recyclerViewSubCategory: RecyclerView
    private lateinit var subCategorys: ArrayList<SubCategory>
    private lateinit var subCategoryAdapter: SubCategoryAdapter
    private lateinit var recyclerViewProduct: RecyclerView
    private lateinit var products: ArrayList<Product>
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_bycategory)
        val product = intent.getParcelableExtra<Category>("category")



        recyclerViewSubCategory = findViewById(R.id.subCat)

        recyclerViewSubCategory.layoutManager = LinearLayoutManager(applicationContext,  LinearLayoutManager.HORIZONTAL, false)
        subCategorys= ArrayList()
        subCategorys.add(SubCategory("1","Lipstick","4"))
        subCategorys.add(SubCategory("2","Lip Gloss","4"))
        subCategorys.add(SubCategory("3","Lip Balm","4"))
        subCategorys.add(SubCategory("4","Foundation","2"))
        subCategorys.add(SubCategory("5","Cream","2"))

        subCategoryAdapter= SubCategoryAdapter(subCategorys)
        recyclerViewSubCategory.adapter = subCategoryAdapter

        /////////////////////////prduct//////////////////////////////

        val TAG = "ProductListFragment"

        ProductRepo.apiService.getProducts().enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    recyclerViewProduct=findViewById(R.id.Products)
                    recyclerViewProduct?.layoutManager = StaggeredGridLayoutManager( 3, StaggeredGridLayoutManager.VERTICAL)

                    val productsList = response.body()
                    productsList?.let{
                        productAdapter = ProductAdapter(productsList)
                        recyclerViewProduct.adapter=productAdapter

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

/*
products = ArrayList()
        products.add( Product("1","TOMFORD","Lip Color Matte Lipstick","",10.10,100,true,false,"","",true,20))
        products.add( Product("1","p3","description","",60.20,100,true,false,"","",true,20))
        products.add( Product("1","p4","description","",10.20,100,true,false,"","",true,20))
        products.add( Product("1","p5","description","",100.50,100,true,false,"","",true,20))
        products.add( Product("1","p6","description","",15.20,100,true,false,"","",true,20))
        products.add( Product("1","p7","description","",66.00,100,true,false,"","",true,20))
        productAdapter = ProductAdapter(products)
        recyclerViewProduct.adapter=productAdapter
        productAdapter.onItemClick={
            val intent = Intent(this,product_detail::class.java)
           // intent.putExtra("product",it)
            startActivity(intent)
        }*/
    }
}