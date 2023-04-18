package tn.farah.smartmakeupapp.data.repo

import retrofit2.http.GET
import tn.farah.smartmakeupapp.data.repo.network.retrofit


interface ProductService {
    @GET("/product/")
    fun getProducts():String
}
object ProductApi{
    val retrofitService : ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}