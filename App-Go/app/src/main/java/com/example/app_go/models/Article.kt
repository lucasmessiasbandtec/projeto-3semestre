package com.example.app_go.models

data class Article(
    val author: Author,
    val title: String,
    val body: String,
    val itinerary: Trip
) {
}