package com.gotravel.application.dto

import javax.validation.constraints.NotEmpty

data class NewItinerary(
        @get:NotEmpty(message = "Starting point cannot be empty.")
        val startingPoint: String,
        @get:NotEmpty(message = "Start Latitude cannot be empty.")
        val startLatitude: String,
        @get:NotEmpty(message = "Start Longitude cannot be empty.")
        val startLongitude: String,
        @get:NotEmpty(message = "Destiny cannot be empty.")
        val endPoint: String,
        @get:NotEmpty(message = "Destiny Latitude point cannot be empty.")
        val destinyLatitude: String,
        @get:NotEmpty(message = "Destiny Longitude cannot be empty.")
        val destinyLongitude: String,
        val filter: MutableList<Filter> = mutableListOf()
)