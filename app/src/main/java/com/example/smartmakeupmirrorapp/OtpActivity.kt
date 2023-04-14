package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse
import com.example.smartmakeupmirrorapp.Retrofit.SharedPrefManager
import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : AppCompatActivity() {
    private lateinit var verify: AppCompatButton
    private lateinit var emaill: TextView
    private lateinit var otpcode: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        emaill = findViewById(R.id.otpEmail)
        otpcode = findViewById(R.id.otpcode)
        verify = findViewById(R.id.verifyBtn)

        emaill.text = intent.extras?.getString("email")

        verify.setOnClickListener {
            OTP(otpcode.text.toString())
        }

    }
    fun OTP(code: String)
    {
        val request = UserRequest()

        request.code = code
        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.otp(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {


                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    val intent = Intent(applicationContext, ChangepassActivity::class.java)
                    intent.putExtra("email", emaill.text.toString())


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