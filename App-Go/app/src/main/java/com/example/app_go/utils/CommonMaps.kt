package com.example.app_go.utils

import com.example.app_go.retrofit.MapsClient
import com.example.appclienterest.ApiGo
import com.example.locaisproximos.Model.Results

object CommonMaps {

    private val GOOGLE_API_URL = "https://maps.googleapis.com/"

    var currentResult: Results?=null

    val googleApiService:ApiGo
        get()=MapsClient.getClient(GOOGLE_API_URL).create(ApiGo::class.java)

}