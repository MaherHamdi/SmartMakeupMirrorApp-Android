package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangepassActivity : AppCompatActivity() {
    private lateinit var change: AppCompatButton
    private lateinit var newPass : EditText
    private lateinit var emailll : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepwd)
        change = findViewById(R.id.changeBtn)
        newPass = findViewById(R.id.newPWD)

        val emaill = intent.extras?.getString("email").toString()

        change.setOnClickListener {
            reset(emaill,newPass.text.toString())
        }

    }
    fun reset(email: String, password: String)
    {
        val request = UserRequest()
        request.email = email
        request.password = password
        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.reset(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {


                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    val intent = Intent(applicationContext, LoginActivity::class.java)



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