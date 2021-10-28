package com.example.app_go.models.request

import com.example.app_go.models.Trip

data class NewArticleRequest(
        val title: String ,
        val description: String ,
        val body: String,
        val itinerary: String
)
