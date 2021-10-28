package com.example.app_go.models.response

import com.example.app_go.models.data.DataItinerary

data class ResponseItinerary(
        val `data`: DataItinerary,
        val erros: List<Any>
)