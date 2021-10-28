package com.gotravel.domain.repositories

import com.gotravel.domain.entities.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findByEmail(email: String?): User?
    fun save(user: User?)
    fun existsByEmail(email: String?): Boolean
    fun existsByUserName(u: String?): Boolean
    fun findByUserName(username: String): User?
    fun findByName(name: String): User?
}