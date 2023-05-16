package tn.farah.smartmakeupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.farah.smartmakeupapp.Adapter.FavProductAdapter
import tn.farah.smartmakeupapp.Adapter.NewProductAdapter
import tn.farah.smartmakeupapp.Adapter.ProductAdapter
import tn.farah.smartmakeupapp.data.models.NewProducts
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.models.ProductsIsFaved
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class Favorite : AppCompatActivity() {
    private lateinit var recyclerViewFav: RecyclerView
    private lateinit var favProductAdapter: FavProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val TAG = "NewProductList"
        val favProducts = ProductsIsFaved(true)
        val items_Nb : TextView = findViewById(R.id.product_item_number_fav)

        ProductRepo.apiService.getFavedProduct(favProducts)
            .enqueue(object : Callback<List<Product>> {


                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    if (response.isSuccessful) {
                        recyclerViewFav = findViewById(R.id.recyclerViewProductFav)
                        recyclerViewFav?.layoutManager =
                            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

                        val newProductsList = response.body()
                        items_Nb.text= newProductsList!!.size.toString()

                        newProductsList?.let {
                            favProductAdapter = FavProductAdapter(newProductsList)
                            recyclerViewFav.adapter = favProductAdapter

                            favProductAdapter.onItemClick = {
                                val intent =
                                    Intent(applicationContext, product_detail::class.java)
                                intent.putExtra("product", it)
                                startActivity(intent)
                                favProductAdapter.notifyDataSetChanged()
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

    }
}