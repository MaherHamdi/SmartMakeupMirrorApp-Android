package tn.farah.smartmakeupapp.data.repo.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import tn.farah.smartmakeupapp.data.models.Favorite
import tn.farah.smartmakeupapp.data.models.NewProducts
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.models.ProductsByCategory

object FavoriteRepo {
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
    val apiService:FavoriteService by lazy {
        retrofit.create(FavoriteService::class.java)
    }
}


interface FavoriteService {

    @GET("favorite/")

    fun getFavorites(): Call<List<Favorite>>

    @POST("favorite/")

    fun postFavorite(@Body favorite: Favorite )

    @PUT("favorite/")
    fun  putFavorite(@Body favorite: Favorite)

}
