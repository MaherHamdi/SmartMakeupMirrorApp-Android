import com.example.smartmakeupmirrorapp.Models.*

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


object ProductRepo{
    private const val  BASE_URL = "http://192.168.1.9:9090/"
    /**
     * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
     */
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val listType = Types.newParameterizedType(List::class.java, Product::class.java)
    val adapter: JsonAdapter<List<Product>> = moshi.adapter(listType)




    /**
     * The OkHttp client with HTTPS support.
     */
    private val okHttpClient = OkHttpClient.Builder()
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()
    /**
     * The Retrofit object with the Moshi converter.
     */

    private val retrofit : Retrofit by lazy {
        //val adapter =   moshi.adapter<Products>()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create( moshi))
            .build()
    }
    val apiService:ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}


interface ProductService {

    @GET("product/")

    fun getProducts():Call<List<Product>>

    @POST("product/New")

    fun getNewProducts(@Body new : NewProducts):Call<List<Product>>

    @POST ("product/ProductByCategory")
    fun  getProductByCategory(@Body category: ProductsByCategory):Call<List<Product>>

    @POST ("product/isFaved")
    fun  getFavedProduct(@Body isFaved : ProductsIsFaved):Call<List<Product>>


    @PUT("product/{id}")
    fun  updateFavedProduct(@Path("id")id:String, @Body isFaved :ProductsIsFaved):Call<Product>

    @POST("product/ProductBySuCategory")
    fun getProductBySubCategory(@Body subCategory: ProductsBySubCategory):Call<List<Product>>

}