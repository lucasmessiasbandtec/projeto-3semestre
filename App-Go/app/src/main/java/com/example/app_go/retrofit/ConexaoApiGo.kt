package com.example.appclienterest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConexaoApiGo {

    fun criar() : ApiGo{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") //LOCAL=http://10.0.2.2:8080/ -  API= http://34.226.233.92/
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiGo::class.java)

        return api
    }
}