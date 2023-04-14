package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.content.Context

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse

import com.example.smartmakeupmirrorapp.Retrofit.SharedPrefManager
import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    lateinit var btnLogin: ImageView
    lateinit var register: TextView
    lateinit var forgetpwd: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.EditTextPersonEmail)
        password = findViewById(R.id.EditTextPersonPassword)
        btnLogin = findViewById(R.id.login)
        register = findViewById(R.id.Register)
        forgetpwd = findViewById(R.id.forgett)

        forgetpwd.setOnClickListener {
            startActivity(Intent(applicationContext, ForgetpassActivity::class.java))
        }


        register.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
        }

        btnLogin.setOnClickListener {
            login(email.text.toString(),password.text.toString())

        }

        }
    fun login(email: String,password:String)
    {
        val request = UserRequest()
        request.email = email
        request.password =password
        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.login(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    // Get the SharedPreferences object for your app
                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    // Get an instance of SharedPreferences.Editor
                    val editor = sharedPreferences.edit()
                    // Save the user's email address in shared preferences
                    editor.putString("password", user!!.user?.password!!)
                    editor.putString("email", user!!.user?.email!!)
                    editor.putString("name", user!!.user?.name!!)
                    editor.putString("birthday", user!!.user?.birthday!!)
                    editor.putString("phone", user!!.user?.phone!!)
                    editor.putString("address",user!!.user?.address!!)
                    // Commit the changes to the SharedPreferences object
                    editor.commit()
                    // Get the user's email address from shared preferences

                    // Get the user's email address from shared preferences
                    val userEmail = sharedPreferences.getString("email", "")

                    // Do something with the user's email address
                    // Do something with the user's email address

                    //SharedPrefManager.getInstance(applicationContext).saveUser(UserRequest)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    val intent = Intent(applicationContext, SettingsActivity::class.java)

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


