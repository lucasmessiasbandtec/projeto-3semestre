package com.gotravel.domain.repositories

import com.gotravel.domain.entities.Itinerary
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ItineraryRepository : MongoRepository<Itinerary, String> {
    fun findByUserId(id: String?, pageable: Pageable): List<Itinerary>
}