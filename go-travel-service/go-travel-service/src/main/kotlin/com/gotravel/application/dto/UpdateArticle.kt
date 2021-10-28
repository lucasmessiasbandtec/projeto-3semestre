package com.gotravel.application.dto

data class UpdateArticle(
        var title: String? = null,
        var description: String? = null,
        var body: String? = null,
        var tagList: List<String>? = null)