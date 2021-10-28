package com.example.app_go.models

data class Trip(
    val endPoint: String,
    val filters: List<Filter>?,
    val id: String,
    val latitudeDestiny: String,
    val latitudeStart: String,
    val longitudeDestiny: String,
    val longitudeStart: String,
    val startingPoint: String
)