package com.gotravel.domain.service

import com.gotravel.application.dto.NewItinerary
import com.gotravel.domain.entities.Itinerary
import com.gotravel.domain.repositories.ItineraryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
@Service
class ItineraryService(val itineraryRepository: ItineraryRepository) {

    fun save(newItinerary: NewItinerary, id: String?): Itinerary {
        var itinerary = Itinerary.fromDocument(newItinerary, id)
        itinerary = itineraryRepository.save(
                itinerary
        )
        return itinerary
    }

    fun update(itinerary: Itinerary) {
        itineraryRepository.save(itinerary)
    }

    fun findBy(currentUserId: String?, pageable: Pageable) = itineraryRepository.findByUserId(currentUserId, pageable)

    fun findById(id: String?) = id?.let { itineraryRepository.findById(it) }

    fun delete(itinerary: Itinerary) = itineraryRepository.delete(itinerary)
}