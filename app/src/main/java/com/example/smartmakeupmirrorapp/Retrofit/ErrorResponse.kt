package com.example.smartmakeupmirrorapp.Retrofit
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class ErrorResponse(val error: Boolean, val message:String)