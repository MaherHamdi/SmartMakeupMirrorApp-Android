package tn.farah.smartmakeupapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import tn.farah.smartmakeupapp.Adapter.ProductAdapter
import tn.farah.smartmakeupapp.Adapter.SubCategoryAdapter
import tn.farah.smartmakeupapp.R.id.category
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
private lateinit var categoryLayout:ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_by_category)
        val category = intent.getParcelableExtra<Category>("category")
        //val categoryLayout: ConstraintLayout = findViewById(R.id.category)

        val TAGSubCategory = "SubCategoryList"
        val categoryforSubCategory = SubCategoryByCategory(category!!._id)
        val textView: TextView = findViewById(R.id.product_name_by_category)
        val items_Nb: TextView = findViewById(R.id.product_item_number)


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