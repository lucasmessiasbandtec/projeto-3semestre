package com.gotravel.domain.repositories

import com.gotravel.domain.entities.Article
import com.gotravel.domain.entities.Comment
import com.gotravel.domain.entities.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : MongoRepository<Article, String>{
    fun existsBySlug(slug: String): Boolean
    fun findByTagListName(tag: String, pageable: Pageable): List<Article>
    fun findByAuthorName(author: String, pageable: Pageable): List<Article>
    fun findByFavorited(fav: String, pageable: Pageable): List<Article>
    fun findBySlug(slug: String): Article?
    fun findByAuthorIdInOrderByCreatedAtDesc(ids: List<String?>?, pageable: Pageable): List<Article>
    fun findAllByAuthorId(id: String?, pageable: Pageable): List<Article>
}