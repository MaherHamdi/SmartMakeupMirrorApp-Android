package tn.farah.smartmakeupapp.data.repo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import tn.farah.smartmakeupapp.data.models.Category

class CategoryRepo {
    private val baseUrl = "http://192.168.106.73:9090/category/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCategories(): Call<List<Category>> {
        val service = retrofit.create(CategoryApiService::class.java)
        return service.getCategories()
    }
}

interface CategoryApiService {
    @GET("categories")
    fun getCategories(): Call<List<Category>>
}