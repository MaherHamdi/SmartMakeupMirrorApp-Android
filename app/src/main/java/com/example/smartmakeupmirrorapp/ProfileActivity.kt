package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse
import com.example.smartmakeupmirrorapp.Retrofit.SharedPrefManager
import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var nameEd: EditText
    private lateinit var emailEd: EditText
    private lateinit var phoneEd: EditText
    private lateinit var addressEd:EditText
    private lateinit var back: ImageView
    private lateinit var dateEd: EditText
    private lateinit var nametxt: TextView
    private lateinit var update: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        nameEd = findViewById(R.id.nameee)
        nametxt = findViewById(R.id.nameTxt)
        emailEd = findViewById(R.id.emailll)
        phoneEd = findViewById(R.id.phoneee)
        addressEd = findViewById(R.id.address)
        dateEd = findViewById(R.id.birthdaaay)
        dateEd.setInputType(InputType.TYPE_NULL)
        update = findViewById(R.id.btn_updt)
        back = findViewById(R.id.back)

        back.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

        dateEd.setOnClickListener {

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
                    dateEd.setText(dat)
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

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val id = sharedPreferences?.getInt("id", 0)
        val name = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        val phone = sharedPreferences?.getString("phone","")
        val date = sharedPreferences?.getString("birthday","")
        val password = sharedPreferences?.getString("password","")
        val address = sharedPreferences?.getString("address","")

        nameEd.setText(name)
        emailEd.setText(email)
        phoneEd.setText(phone)
        dateEd.setText(date)
        addressEd.setText(address)

        nametxt.text= name

        update.setOnClickListener {
            update(emailEd.text.toString(),nameEd.text.toString(), phoneEd.text.toString(), dateEd.text.toString(), addressEd.text.toString())
        }



    }
    fun update(email: String,name:String,phone:String,dateEd:String, address: String)
    {
        val request = UserRequest()
        request.email = email
        request.phone = phone
        request.name = name
        request.birthday = dateEd
        request.address = address
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val emaill = sharedPreferences?.getString("email", "")
        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.update(emaill.toString(),request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {


                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val intent = Intent(applicationContext, SettingsActivity::class.java)

                startActivity(intent)


            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
            }
        })
    }
}