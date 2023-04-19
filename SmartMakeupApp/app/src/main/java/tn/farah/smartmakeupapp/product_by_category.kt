package tn.farah.smartmakeupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import tn.farah.smartmakeupapp.Adapter.ProductAdapter
import tn.farah.smartmakeupapp.Adapter.SubCategoryAdapter
import tn.farah.smartmakeupapp.data.models.Category
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.models.SubCategory
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class product_by_category : AppCompatActivity() {
    private lateinit var recyclerViewSubCategory: RecyclerView
    private lateinit var subCategorys: ArrayList<SubCategory>
    private lateinit var subCategoryAdapter: SubCategoryAdapter
    private lateinit var recyclerViewProduct: RecyclerView
    private lateinit var products: ArrayList<Product>
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_by_category)
        val product = intent.getParcelableExtra<Category>("category")



        recyclerViewSubCategory = findViewById(R.id.recyclerViewSubCategory)

        recyclerViewSubCategory.layoutManager = GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false)
        subCategorys= ArrayList()
        subCategorys.add(SubCategory("1","Lipstick","4"))
        subCategorys.add(SubCategory("2","Lip Gloss","4"))
        subCategorys.add(SubCategory("3","Lip Balm & Treatment","4"))
        subCategorys.add(SubCategory("4","Foundation","2"))
        subCategorys.add(SubCategory("5","BB & CC Cream","2"))

        subCategoryAdapter= SubCategoryAdapter(subCategorys)
        recyclerViewSubCategory.adapter = subCategoryAdapter

                                 /////////////////////////prduct//////////////////////////////

        val TAG = "ProductListFragment"

        ProductRepo.apiService.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    recyclerViewProduct=findViewById(R.id.recyclerViewProduct)
                    recyclerViewProduct?.layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)

                    val productsList = response.body()
                    productsList?.let{
                        productAdapter = ProductAdapter(productsList)
                        recyclerViewProduct.adapter=productAdapter

                        productAdapter.onItemClick={
                            val intent = Intent(applicationContext,product_detail::class.java)
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