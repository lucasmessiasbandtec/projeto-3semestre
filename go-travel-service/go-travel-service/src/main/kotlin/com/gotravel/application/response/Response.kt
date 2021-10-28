package com.gotravel.application.response

data class Response<T> (
        var data: T? = null,
        var erros: ArrayList<String> = arrayListOf()
)