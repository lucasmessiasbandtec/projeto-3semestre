package com.gotravel.application.dto

import java.util.*
import javax.validation.constraints.NotEmpty

data class Filter (
    val id: String? = UUID.randomUUID().toString(),
    @get:NotEmpty(message = "Name cannot be empty")
    val name: String,
    val rua: String,
    val image: String
)
