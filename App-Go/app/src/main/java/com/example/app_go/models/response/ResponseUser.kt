package com.example.app_go.models.response

import com.example.app_go.models.data.DataUser

data class ResponseUser(
        val `data`: DataUser,
        val erros: List<Any>
)