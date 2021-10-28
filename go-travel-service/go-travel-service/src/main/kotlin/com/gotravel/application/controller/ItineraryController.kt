package com.gotravel.application.controller

import com.gotravel.application.dto.Filter
import com.gotravel.application.dto.NewItinerary
import com.gotravel.application.response.Response
import com.gotravel.application.validations.InvalidRequest
import com.gotravel.domain.entities.Itinerary
import com.gotravel.domain.service.ItineraryService
import com.gotravel.domain.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import javax.validation.Valid


@RestController
@RequestMapping("/itinerary")
class ItineraryController(val userService: UserService, val itineraryService: ItineraryService) {

    @PostMapping
    fun newItinerary(@Valid @RequestBody newItinerary: NewItinerary, result: BindingResult): ResponseEntity<Response<Any>> {
        val response: Response<Any> = Response<Any>()

        InvalidRequest.check(response, result)

        if (response.erros.isNotEmpty()) {
            return ResponseEntity.badRequest().body(response)
        }

        val currentUser = userService.currentUser()
        val itinerary = itineraryService.save(newItinerary, currentUser?.id)

        response.data = itineraryView(itinerary)

        return ResponseEntity.ok().body(response)
    }

    @GetMapping
    fun itinerarys(@RequestParam(defaultValue = "20") limit: Int,
                   @RequestParam(defaultValue = "0") offset: Int): ResponseEntity<Response<Any>> {
        val response: Response<Any> = Response<Any>()

        val currentUser = userService.currentUser()
        val page = itineraryService.findBy(currentUser?.id, PageRequest.of(offset, limit))

        response.data = itinerarysView(page)

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteItinerary(@PathVariable id: String): ResponseEntity<Response<Any>> {
        try {
            itineraryService.findById(id)?.let {
                return if (it?.get().userId != userService.currentUser()?.id) {
                    ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                } else {
                    itineraryService.delete(it.get())
                    ResponseEntity.ok().build()
                }
            }
        } catch (e: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/filter/{id}")
    fun newFilter(@PathVariable id: String, @Valid @RequestBody filter: Filter, result: BindingResult): ResponseEntity<Response<Any>> {
        val response: Response<Any> = Response<Any>()

        InvalidRequest.check(response, result)

        if (response.erros.isNotEmpty()) {
            return ResponseEntity.badRequest().body(response)
        }

        itineraryService.findById(id)?.let {
            return if (it?.get().userId != userService.currentUser()?.id) {
                ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            } else {
                it.get().filters.add(filter)
                itineraryService.update(it.get())
                ResponseEntity.ok().build()
            }
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}/filter/{uulid}")
    fun deleteFilter(@PathVariable id: String, @PathVariable uulid: String): ResponseEntity<Response<Any>> {
        itineraryService.findById(id)?.let {
            if (it?.get().userId != userService.currentUser()?.id) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            } else {
                it.get().filters.map { f ->
                    if (f.id == uulid) {
                        it.get().filters.remove(f)
                        itineraryService.update(it.get())
                        return ResponseEntity.ok().build()
                    }
                }
                return ResponseEntity.notFound().build()
            }
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/place_image/{photoreference}/{width}/{height}")
    fun getPlaceImage(
            @PathVariable photoreference: String,
            @PathVariable width: String,
            @PathVariable height: String): ResponseEntity<*>? {
        val base_uri = "https://maps.googleapis.com/maps/api/place/photo?"
        val uri = "https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBw46FEvXL1fBBgw8bocxI-fYTcva5yTeQ&photoreference=$photoreference&maxwidth=$width&maxheight= $height"
        val webClient: WebClient = WebClient.create(uri)
        val result: Mono<String> = webClient.get()
                .uri { uriBuilder -> uriBuilder.build() }
                .retrieve()
                .bodyToMono(String::class.java)
        val spitted: List<String>? = result.block()?.split("<")
        if (spitted != null) {
            for (x in spitted.indices) {
                if (spitted[x].startsWith("A HREF")) {
                    val anotherSplitted = spitted[x].split("HREF=\"".toRegex()).toTypedArray()
                    val oneMore = anotherSplitted[1].split("\">here".toRegex()).toTypedArray()
                    return ResponseEntity.ok(oneMore[0])
                }
            }
        }
        return ResponseEntity.notFound().build<Any>()
    }


    @GetMapping("/place_location/{lat}/{lng}/{radius}/{filterSelected}")
    fun getPlaceLocation(@PathVariable lat: String, @PathVariable lng: String, @PathVariable radius: String, @PathVariable filterSelected: String): ResponseEntity<*>? {
        val uri = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$lat,$lng&radius=$radius&language=pt-BR&type=$filterSelected&key=AIzaSyBw46FEvXL1fBBgw8bocxI-fYTcva5yTeQ"
        val webClient = WebClient.create(uri)
        val result = webClient.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder.build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
        return ResponseEntity.ok().body(result.block())
    }

    fun itineraryView(itinerary: Itinerary) = mapOf("itinerary" to itinerary)
    fun itinerarysView(itinerary: List<Itinerary>) = mapOf("itinerarys" to itinerary)
}