package tn.farah.smartmakeupapp
import tn.farah.smartmakeupapp.data.models.Product
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView


import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.farah.smartmakeupapp.Adapter.CategoryAdapter
import tn.farah.smartmakeupapp.Adapter.NewProductAdapter
import tn.farah.smartmakeupapp.Adapter.ProductCamAdapter
import tn.farah.smartmakeupapp.data.models.Category
import tn.farah.smartmakeupapp.data.models.NewProducts
import tn.farah.smartmakeupapp.data.repo.CategoryRepo
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

class AcceuilActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewNewProduct: RecyclerView

    private lateinit var newProductAdapter: NewProductAdapter

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var adapter: ProductCamAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)
        //////////////////////////////////////////////////////////////////////////////////////

      //  val session = Session(this)
//
// Set a camera configuration that usese the front-facing camera.
    /*    val filter = CameraConfigFilter(session).setFacingDirection(CameraConfig.FacingDirection.FRONT)
        val cameraConfig = session.getSupportedCameraConfigs(filter)[0]
        session.cameraConfig = cameraConfig
        val config = Config(session)
        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
        session.configure(config)*/

//////////////////////////////
        val buttonOpenCam = findViewById<FloatingActionButton>(R.id.openCam)
        buttonOpenCam.setOnClickListener {

       //     var intent = Intent(this, Camera::class.java)

            var intent = Intent(this,list_video::class.java)
            startActivity(intent)}

            val imageViewFav = findViewById<ImageView>(R.id.imageViewfav)
            imageViewFav.setOnClickListener{
                var intent = Intent(this, Favorite::class.java)
                startActivity(intent)
            }
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
                                val intent =
                                    Intent(applicationContext, product_by_category::class.java)
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
                                    val intent =
                                        Intent(applicationContext, product_detail::class.java)
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

        }
        fun showToast(str: String) {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }


    }




