package com.example.smartmakeupmirrorapp.Retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.smartmakeupmirrorapp.Models.UserResponse
import com.google.gson.Gson

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()
    val isLoggedIn: Boolean
        get() {
            sharedPreferences
            return sharedPreferences.getInt("id", -1) != -1
        }
    val user: UserResponse?
        get() {
            //sharedPreferences
            val json = sharedPreferences.getString("user", "")
            return if (json.isNullOrEmpty()) null else gson.fromJson(json, UserResponse::class.java)

        }
            fun putUser(user: UserResponse) {
        val json = gson.toJson(user)

        editor.putString("user", json)
        editor.apply()
    }



    fun removeUser() {
        editor.remove("user")
        editor.apply()
    }
}