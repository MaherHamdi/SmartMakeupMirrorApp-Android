package com.example.smartmakeupmirrorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("user")
    @Expose
    var user: User? =null
    class User{
        @SerializedName("id")
        @Expose
        var id:Int? =null
        @SerializedName("email")
        @Expose
        var email:String? =null
        @SerializedName("password")
        @Expose
        var password:String? =null
        @SerializedName("name")
        @Expose
        var name:String? =null
        @SerializedName("phone")
        @Expose
        var phone:String? =null
        @SerializedName("birthday")
        @Expose
        var birthday:String? =null
        @SerializedName("code")
        @Expose
        var code:String? =null
        @SerializedName("address")
        @Expose
        var address:String? =null
    }
}