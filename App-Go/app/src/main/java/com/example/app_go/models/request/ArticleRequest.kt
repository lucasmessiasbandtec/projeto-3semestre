package com.example.app_go.models.request

import com.example.app_go.models.Author
import com.example.app_go.models.Itinerary
import com.example.app_go.models.Trip

data class ArticleRequest (
        val author: AuthorRequest,
        val title: String,
        val body: String,
        val itinerary: Trip?
        )