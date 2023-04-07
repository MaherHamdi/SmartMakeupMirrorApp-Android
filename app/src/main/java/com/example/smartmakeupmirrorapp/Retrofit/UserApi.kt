package com.example.smartmakeupmirrorapp.Retrofit

import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @GET("users")
    fun getUsers(): Call<List<UserRequest>>

    // Works exactly the same, as above
    @HTTP(method = "GET", path = "users")
    fun httpUsers(): Call<List<UserRequest>>
    @POST("login")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>
    @POST("add")
    fun register(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    @PUT("users")
    fun putUsers(): Call<List<UserRequest>>

    @PATCH("users")
    fun patchUsers(): Call<List<UserRequest>>

    @DELETE("users")
    fun deleteUsers(): Call<List<UserRequest>>

    @DELETE("users")
    fun failingDeleteUsers(@Body user: UserRequest): Call<UserRequest>

    @HTTP(method = "DELETE", path = "users", hasBody = true)
    fun workingDeleteUsers(@Body user: UserRequest): Call<UserRequest>

    @OPTIONS("users")
    fun optionsUsers(): Call<List<UserRequest>>

    // Must be Void
    @HEAD("users")
    fun headUsers(): Call<Void>

    // Replaces the BASE_URL
    @GET("http://localhost:8090/v3/users")
    fun getUsersV3(): Call<List<UserRequest>>

    @GET
    fun getUsersDynamic(@Url url: String): Call<List<UserRequest>>

    @GET("users/{userId}")
    fun getUserById(@Path("userId") userId: Int): Call<UserRequest>

    @GET("users?sort_order=asc")
    fun getUsersStaticQueryParam(): Call<List<UserRequest>>

    @GET("users")
    fun getUsersDynamicQueryParam(@Query("sort_order") order: String): Call<List<UserRequest>>

    @GET("users")
    fun getUsersDynamicQueryMap(@QueryMap parameters: Map<String, String>): Call<List<UserRequest>>

    @Headers("User-Agent: codersee-application")
    @GET("users")
    fun getUsersSingleStaticHeader(): Call<List<UserRequest>>

    @Headers(
        value = [
            "User-Agent: codersee-application",
            "Custom-Header: my-custom-header"
        ]
    )
    @GET("users")
    fun getUsersMultipleStaticHeaders(): Call<List<UserRequest>>

    @GET("users")
    fun getUsersDynamicHeader(@Header("Authorization") token: String): Call<List<UserRequest>>

    @GET("users")
    fun getUsersHeaderMap(@HeaderMap headers: Map<String, String>): Call<List<UserRequest>>

    @POST("users")
    fun postUsersWithPayload(@Body user: UserRequest): Call<UserRequest>

    @FormUrlEncoded
    @POST("users")
    fun postUsersFormUrlEncoded(@Field("field_one") fieldOne: String): Call<UserRequest>

    @Multipart
    @POST("users")
    fun postUsersMultipart(@Part("something") partOne: RequestBody): Call<UserRequest>
}