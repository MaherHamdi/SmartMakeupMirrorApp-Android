package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse
import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetpassActivity : AppCompatActivity() {
    private lateinit var sendOTP: AppCompatButton
    private lateinit var emailOTP: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpwd)
        sendOTP = findViewById(R.id.sendBtn)
        emailOTP = findViewById(R.id.emailOtp)


        sendOTP.setOnClickListener {
            sendOTP(emailOTP.text.toString())

        }

    }
    fun sendOTP(email: String)
    {
        val request = UserRequest()
        request.email = email
        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.forgotPwd(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {


                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    val intent = Intent(applicationContext, OtpActivity::class.java)
                    intent.putExtra("email", emailOTP.text.toString())


                    startActivity(intent)

                }
                else{
                    Toast.makeText(applicationContext, "must not be empty!!!", Toast.LENGTH_SHORT).show()

                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
            }
        })
    }
}