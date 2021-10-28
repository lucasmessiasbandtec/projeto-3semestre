package com.example.app_go.models.request

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class SignupRequest(
        @SerializedName("name")
        val name:String,

        @SerializedName("username")
        @get:Nullable
        val username:String?,

        @SerializedName("bio")
        val bio:String,

        @SerializedName("email")
        val email:String,

        @SerializedName("password")
        val password:String
)