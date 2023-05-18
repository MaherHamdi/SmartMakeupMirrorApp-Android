package com.example.smartmakeupmirrorapp.Models

data class ChangePasswordRequest(
    var oldPassword: String,
    var newPassword: String
)