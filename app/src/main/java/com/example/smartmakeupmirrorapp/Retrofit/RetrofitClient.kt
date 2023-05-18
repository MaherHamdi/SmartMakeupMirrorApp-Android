import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.*

import com.google.gson.GsonBuilder


class RetrofitClient {
    fun getInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.9:9090/user/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}