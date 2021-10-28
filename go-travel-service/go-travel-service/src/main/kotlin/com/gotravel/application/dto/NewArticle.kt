package com.gotravel.application.dto

import com.gotravel.domain.entities.Article
import com.gotravel.domain.entities.Itinerary
import com.gotravel.domain.entities.Tag
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty

data class NewArticle(
        @get:NotEmpty(message = "title cannot be empty.")
        var title: String? = null,

        @get:NotEmpty(message = "description cannot be empty.")
        var description: String? = null,

        @get:NotEmpty(message = "body cannot be empty.")
        var body: String? = null,

        var itinerary: String?,

        var tagList: List<String> = listOf()
) {
    companion object {
        fun dateFormat(): String {
            val a = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return a.format(LocalDateTime.now())
        }

        fun toDocument(article: NewArticle, slug: String, author: Author, tagList: List<Tag>, itinerary: Itinerary?): Article {
            return Article(
                    slug = slug,
                    author = author,
                    title = article.title,
                    description = article.description,
                    body = article.body,
                    itinerary = itinerary,
                    tagList = tagList.toMutableList()
            )
        }
    }
}