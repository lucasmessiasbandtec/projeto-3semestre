package com.example.app_go.models.request

import com.example.app_go.models.Filter

data class NewTripRequest(
    val destinyLatitude: String,
    val destinyLongitude: String,
    val endPoint: String,
    val startLatitude: String,
    val startLongitude: String,
    val startingPoint: String,
    val filter: List<Filter>?
)