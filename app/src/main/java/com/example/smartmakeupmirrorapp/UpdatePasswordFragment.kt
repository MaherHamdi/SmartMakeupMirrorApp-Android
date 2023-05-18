package com.example.smartmakeupmirrorapp

import RetrofitClient
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.smartmakeupmirrorapp.Models.ChangePasswordRequest
import com.example.smartmakeupmirrorapp.Models.UserRequest
import com.example.smartmakeupmirrorapp.Models.UserResponse
import com.example.smartmakeupmirrorapp.Retrofit.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePasswordFragment: DialogFragment() {

    private lateinit var email: String
    private lateinit var oldPwd: EditText
    private lateinit var newPwd: EditText
    private lateinit var confirmPassword : EditText
    private lateinit var change: AppCompatButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
        val view = inflater.inflate(R.layout.dialog_update_password, container, false)
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        email = sharedPreferences.getString("email", "") ?: ""
        oldPwd = view.findViewById(R.id.oldPWD)
        newPwd = view.findViewById(R.id.newPWD)
        change = view.findViewById(R.id.changeBtn)
        confirmPassword = view.findViewById(R.id.confirmPWD)
        change.setOnClickListener {
            update(oldPwd.text.toString(),newPwd.text.toString())
        }
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (newPwd.text.toString() == confirmPassword.text.toString() && newPwd.text.toString()
                        .isNotEmpty() && confirmPassword.text.toString().isNotEmpty()
                ) {
                    change.isEnabled = true
                    change.setBackgroundColor(Color.rgb(96,58,145))
                } else {
                    change.isEnabled = false
                    change.setBackgroundColor(Color.GRAY)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }
        newPwd.addTextChangedListener(textWatcher)
        confirmPassword.addTextChangedListener(textWatcher)

        return view


    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun update(oldPassword : String,newPassword: String) {
        val request = ChangePasswordRequest(oldPassword, newPassword)


        val retro = RetrofitClient().getInstance().create(UserApi::class.java)
        retro.changePassword(email, request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Password changed successfully
                    val intent = Intent(context, SettingsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    // Handle error
                    Log.e("Error", "Failed to change password")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle error
                t.message?.let { Log.e("Error", it) }
            }
        })
    }
}

