package com.example.app_go.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.app_go.R
import com.example.app_go.models.request.SignupRequest
import com.example.appclienterest.ConexaoApiGo
import com.jaeger.library.StatusBarUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignUpScreen : AppCompatActivity() {

    lateinit  var etNome: EditText
    lateinit  var etEmail: EditText
    lateinit  var etPassword: EditText

    var valido = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        StatusBarUtil.setTransparent(this)

        etNome = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)

    }

    fun validar(){

        valido = true

        if(etNome.text.toString().length < 1){
            etNome.error = "O nome não ser vazio"
            valido = false
        }

        if(etEmail.text.toString().length < 1){
            etEmail.error = "O email não ser vazio"
            valido = false
        }

        if(etPassword.text.toString().length < 1){
            etPassword.error = "O nome não pode estar vazio"
            valido = false
        }
    }

    fun login(view: View) {
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }

    fun create_acc(view: View) {
        validar()
        val intent = Intent(this, LoginScreen::class.java)

        val name: EditText = findViewById(R.id.et_name)
        val email: EditText = findViewById(R.id.et_email)
        val password: EditText = findViewById(R.id.et_password)

        val apiFilmes = ConexaoApiGo.criar()

        apiFilmes.signup(SignupRequest(name = name.text.toString(),username = email.text.toString(), bio = "Olá, entrei no Go!!", email = email.text.toString(), password = password.text.toString())).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code().toString() == "200") {
                    Toast.makeText(baseContext, "Conta criada!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                } else {
                    Toast.makeText(baseContext, "Ocorreu algum problema!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}