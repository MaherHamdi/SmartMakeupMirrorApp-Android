package tn.farah.smartmakeupapp
import tn.farah.smartmakeupapp.data.models.Product
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.farah.smartmakeupapp.Adapter.CategoryAdapter
import tn.farah.smartmakeupapp.Adapter.NewProductAdapter
import tn.farah.smartmakeupapp.Adapter.ProductAdapter
import tn.farah.smartmakeupapp.data.models.Category
import tn.farah.smartmakeupapp.data.models.NewProducts
import tn.farah.smartmakeupapp.data.repo.CategoryRepo
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class AcceuilActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewNewProduct: RecyclerView
    private lateinit var newProducts: ArrayList<Product>

    private lateinit var newProductAdapter: NewProductAdapter

    private lateinit var categoryAdapter: CategoryAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)


        //////////////////////////////getCategory/////////////////////////////////////////////////

        val TAG = "Category"

        CategoryRepo.apiService.getGategorys().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    recyclerView = findViewById(R.id.categoryList)

                    recyclerView?.layoutManager =
                        StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

                    val categorysList = response.body()
                    categorysList?.let {
                        categoryAdapter = CategoryAdapter(categorysList)
                        recyclerView.adapter = categoryAdapter

                        categoryAdapter.onItemClick = {
                            val intent = Intent(applicationContext, product_by_category::class.java)
                            intent.putExtra("category", it)
                            startActivity(intent)
                        }
                    }

                    Log.e(TAG, "Response  successful. : ${categorysList}")

                } else {
                    Log.e(TAG, "Response not successful. Status code: ${response.code()}")
                }


            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e(TAG, "Network request failed", t)
            }
        })










        //////////////////////////////new_product/////////////////////////////////////////////////
        val TAG1 = "NewProductList"
        val newProducts = NewProducts(true)
        ProductRepo.apiService.getNewProducts(newProducts)
            .enqueue(object : Callback<List<Product>> {


                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    if (response.isSuccessful) {
                        recyclerViewNewProduct = findViewById(R.id.newInrecyclerView)
                        recyclerViewNewProduct?.layoutManager =
                            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

                        val newProductsList = response.body()
                        newProductsList?.let {
                            newProductAdapter = NewProductAdapter(newProductsList)
                            recyclerViewNewProduct.adapter = newProductAdapter

                            newProductAdapter.onItemClick = {
                                val intent = Intent(applicationContext, product_detail::class.java)
                                intent.putExtra("product", it)
                                startActivity(intent)
                            }
                        }
                        Log.e(TAG, "Response  successful. : ${newProductsList}")

                    } else {
                        Log.e(TAG, "Response not successful. Status code: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.e(TAG, "Network request failed", t)
                }
            })


        fun showToast(str: String) {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }

    }
}



