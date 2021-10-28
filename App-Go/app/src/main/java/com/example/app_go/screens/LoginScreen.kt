package com.example.app_go.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.app_go.R
import com.example.app_go.models.request.LoginRequest
import com.example.app_go.sessionManager.SessionManager
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginScreen : AppCompatActivity() {

    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        StatusBarUtil.setTransparent(this)

        pref = getSharedPreferences("autenticacao", Context.MODE_PRIVATE)
    }

    fun signup(view: View) {
        val intent = Intent(this, SignUpScreen::class.java)
        startActivity(intent)
    }

    fun login(view: View) {
        val sessionManager = SessionManager(this)
        val intent = Intent(this, HomeScreen::class.java)

        val email: EditText = findViewById(R.id.et_email)
        val password: EditText = findViewById(R.id.et_password)

        val apiFilmes = ConexaoApiGo.criar()

        apiFilmes.login(LoginRequest(email = email.text.toString(), password = password.text.toString())).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                val token = response.headers()["Authorization"]

                if (response.code().toString() == "200") {

                    val edit = pref.edit()
                    edit.putBoolean("logged", true)
                    edit.commit()

                    Toast.makeText(baseContext, "Autenticado!", Toast.LENGTH_SHORT).show()
                    if (token != null) {
                        sessionManager.saveAuthToken(token)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(baseContext, "Falha na autenticação!", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
               Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
                //startActivity(intent)
            }

        })
    }
}