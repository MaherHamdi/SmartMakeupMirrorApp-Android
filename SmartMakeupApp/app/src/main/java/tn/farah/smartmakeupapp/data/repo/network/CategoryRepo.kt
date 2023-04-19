package tn.farah.smartmakeupapp.data.repo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import tn.farah.smartmakeupapp.data.models.Category
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.repo.network.ProductRepo

object CategoryRepo {
    /**
     * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
     */
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
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
        Retrofit.Builder()
            .baseUrl(ProductRepo.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create( moshi))
            .build()
    }
    val apiService:CategoryService by lazy {
        retrofit.create(CategoryService::class.java)
    }
}


interface CategoryService {

    @GET("category/")

    fun getGategorys():Call<List<Category>>


}
