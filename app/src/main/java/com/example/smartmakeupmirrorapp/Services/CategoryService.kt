package com.example.smartmakeupmirrorapp.Models


import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {
    @GET("get")
    fun getCategories() : Call<List<Category>>
}