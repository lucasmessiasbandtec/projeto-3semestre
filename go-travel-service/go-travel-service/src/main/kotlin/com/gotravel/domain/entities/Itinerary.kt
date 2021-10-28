package com.gotravel.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gotravel.application.dto.Filter
import com.gotravel.application.dto.NewItinerary
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Itinerary(
        @Id val id: String? = null,
        @JsonIgnore
        val userId: String?,
        val startingPoint: String,
        val latitudeStart: String,
        val longitudeStart: String,
        val endPoint: String,
        val latitudeDestiny: String,
        val longitudeDestiny: String,
        var filters: MutableList<Filter> = mutableListOf()
) {
    companion object {
        fun fromDocument(itinerary: NewItinerary, currentUserId: String?): Itinerary {
            return Itinerary(
                    userId = currentUserId,
                    startingPoint = itinerary.startingPoint,
                    latitudeStart = itinerary.startLatitude,
                    longitudeStart = itinerary.startLongitude,
                    endPoint = itinerary.endPoint,
                    longitudeDestiny = itinerary.destinyLongitude,
                    latitudeDestiny = itinerary.destinyLatitude,
                    filters = itinerary.filter
            )
        }
    }
}