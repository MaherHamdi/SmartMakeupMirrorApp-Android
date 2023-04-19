package tn.farah.smartmakeupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import tn.farah.smartmakeupapp.Adapter.ProductAdapter
import tn.farah.smartmakeupapp.Adapter.SubCategoryAdapter
import tn.farah.smartmakeupapp.data.models.*
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo
import tn.farah.smartmakeupapp.data.repo.network.SubCategoryRepo

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
        val category = intent.getParcelableExtra<Category>("category")

        val TAGSubCategory = "SubCategoryList"
val categoryforSubCategory = SubCategoryByCategory(category!!._id)
         val textView : TextView = findViewById(R.id.product_name_by_category)
        val items_Nb : TextView = findViewById(R.id.product_item_number)


        textView.text=category!!.name
        SubCategoryRepo.apiService.getSubCategoryByCategorys(categoryforSubCategory).enqueue(object : Callback<List<SubCategory>> {
            override fun onResponse(call: Call<List<SubCategory>>, response: Response<List<SubCategory>>) {
                if (response.isSuccessful) {
                    recyclerViewSubCategory = findViewById(R.id.recyclerViewSubCategory)

                    recyclerViewSubCategory.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

                    val subCategorysList = response.body()
                    subCategorysList?.let{
                        subCategoryAdapter= SubCategoryAdapter(subCategorysList)
                        recyclerViewSubCategory.adapter = subCategoryAdapter

                    }

                    Log.e(TAGSubCategory, "Response  successful. : ${subCategorysList}")

                } else {
                    Log.e(TAGSubCategory, "Response not successful. Status code: ${response.code()}")
                }


            }

            override fun onFailure(call: Call<List<SubCategory>>, t: Throwable) {
                Log.e(TAGSubCategory, "Network request failed", t)
            }
        })














                                 /////////////////////////prduct//////////////////////////////
        val spaceHeight = resources.getDimensionPixelSize(R.dimen.right)
        val itemDecoration = CustomItemDecoration(spaceHeight)
        val TAG = "ProductList"
val categoryForProduct = ProductsByCategory(category!!._id)
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

    }
}