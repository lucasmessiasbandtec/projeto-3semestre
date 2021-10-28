package com.example.app_go.models.response

import com.example.app_go.models.data.DataArticle

data class ResponseArticle(
        val `data`: DataArticle,
        val erros: List<Any>
) {

}