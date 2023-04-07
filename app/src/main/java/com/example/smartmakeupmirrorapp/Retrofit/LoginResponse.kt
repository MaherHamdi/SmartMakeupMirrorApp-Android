package com.example.smartmakeupmirrorapp.Retrofit

import com.example.smartmakeupmirrorapp.Models.UserRequest


data class LoginResponse(val error: Boolean, val message:String, val user: UserRequest)