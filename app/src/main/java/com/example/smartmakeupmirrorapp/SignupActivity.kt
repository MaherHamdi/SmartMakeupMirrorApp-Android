package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse
import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignupActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var name: EditText
    private lateinit var dateEdt: EditText
    lateinit var btnSignup: ImageView
    lateinit var login: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        dateEdt  = findViewById(R.id.idEdtDate)
         email = findViewById(R.id.EditTextPersonEmail2)
         password = findViewById(R.id.EditTextPersonPassword2)
        phone = findViewById(R.id.EditTextPersonPhone2)
         name = findViewById(R.id.EditTextPersonName2)
        btnSignup = findViewById(R.id.sign2)
        login = findViewById(R.id.login)
        dateEdt.setInputType(InputType.TYPE_NULL);


        login.setOnClickListener{
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        dateEdt.setOnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()
            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dateEdt.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day


            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.setOnShowListener {
                val headerId = resources.getIdentifier("date_picker_header", "id", "android")
                val headerView = datePickerDialog.findViewById<View>(headerId)
               //  headerView?.setBackgroundColor(color)


            }

            datePickerDialog.show()
        }
        // on below line we are adding
        // click listener for our edit text.

        btnSignup.setOnClickListener {

            //val birthday = dateEdt.text.toString().trim()
            register(email.text.toString(),password.text.toString(),name.text.toString(),phone.text.toString(),dateEdt.text.toString())




        }
    }
    fun register(email: String,password:String,name:String,phone:String,dateEdt:String)
    {
        val request = UserRequest()
        request.email = email
        request.password =password
        request.phone = phone
        request.name = name
        request.birthday = dateEdt


        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.register(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {


                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val intent = Intent(applicationContext, LoginActivity::class.java)

                startActivity(intent)


            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
            }
        })
    }
}

