package com.example.app_go.sessionManager

import android.content.Context
import android.content.SharedPreferences
import com.example.app_go.R

class SessionManager (context: Context) {
     var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putBoolean("logged", true)
        editor.apply()
    }

    fun loggout(){
        val editor = prefs.edit()
        editor.putBoolean("logged", false)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}