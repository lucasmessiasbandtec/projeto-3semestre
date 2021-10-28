package com.example.app_go

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.app_go.screens.HomeScreen
import com.example.app_go.screens.LoginScreen
import com.example.app_go.screens.SignUpScreen
import com.example.app_go.sessionManager.SessionManager
import com.jaeger.library.StatusBarUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setTransparent(this)

        val sessionManager = SessionManager(this)
        val isLogged = sessionManager.prefs.getBoolean("logged",false)
        if (isLogged) startActivity(Intent(this, HomeScreen::class.java))
    }

    fun login(view: View) {
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }

    fun signup(view: View) {
        val intent = Intent(this, SignUpScreen::class.java)
        startActivity(intent)
    }
}