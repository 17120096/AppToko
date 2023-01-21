package com.example.apptoko

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.apptoko.api.BaseRetrofit
import com.example.apptoko.response.login.LoginResponse
import com.example.apptoko.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object{
        lateinit var sessionManager: SessionManager
        private lateinit var context: Context
    }

    private  val api by lazy { BaseRetrofit().endpoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        val loginStatus = sessionManager.getBoolean("LOGIN_STATUS")
        if (loginStatus){
            val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }


        val btnLogin = findViewById(R.id.btnLogin) as Button
        val txtEmail = findViewById(R.id.txtEmail) as TextInputEditText
        val txtPassword = findViewById(R.id.txtPassword) as TextInputEditText

        btnLogin.setOnClickListener{


            api.login(txtEmail.text.toString(), txtPassword.text.toString()).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.e("Login Data", response.toString())

                    val correct = if (response.body() != null) response.body()!!.success else false

                    if (correct){
                        val token = response.body()!!.data.token

                        sessionManager.saveString("TOKEN", "Bearer $token")
                        sessionManager.saveBoolean("LOGIN_STATUS", true)

                        val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(moveIntent)
                        finish()

                        Toast.makeText(applicationContext,"Login berhasil!", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext,"Email atau password Anda salah!", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Login gagal diproses", t.toString())
                }

                })
        }

    }
}