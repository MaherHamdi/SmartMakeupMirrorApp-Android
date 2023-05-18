package com.example.smartmakeupmirrorapp.Retrofit

import com.example.smartmakeupmirrorapp.Models.SubCategory
import com.example.smartmakeupmirrorapp.Models.SubCategoryByCategory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object SubCategoryRepo {
    private const val  BASE_URL = "http://192.168.1.9:9090/"

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
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    val apiService:SubCategoryService by lazy {
        retrofit.create(SubCategoryService::class.java)
    }
}


interface SubCategoryService {

    @POST("subCategory/byCategory")

    fun getSubCategoryByCategorys(@Body category: SubCategoryByCategory): Call<List<SubCategory>>
}
