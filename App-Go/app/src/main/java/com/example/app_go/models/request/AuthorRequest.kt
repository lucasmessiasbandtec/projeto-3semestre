package com.example.app_go.models.request

import android.graphics.Bitmap

data class AuthorRequest(
        val id:String,
        val image: Bitmap?,
        val name: String,
        val email: String,
)