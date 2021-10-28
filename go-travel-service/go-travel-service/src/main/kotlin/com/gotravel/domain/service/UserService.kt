package com.gotravel.domain.service

import com.gotravel.application.dto.UserRequest
import com.gotravel.domain.entities.User
import com.gotravel.domain.repositories.UserRepository
import com.gotravel.resources.security.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(private val repository: UserRepository,
                  private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    fun findByEmail(email: String?) = repository.findByEmail(email)

    fun save(user: UserRequest) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        val document = UserRequest.toDocument(user)
        repository.save(document)
    }
//    return Base64.getEncoder().encodeToString(photo.getImage().getData());

    fun update(user: User?) {
        repository.save(user)
    }

    fun findByUsername(userName: String) = repository.findByUserName(userName)

    fun findByName(name: String) = repository.findByName(name)

    fun existsByEmail(email: String?) = repository.existsByEmail(email)

    fun existsByUserName(u: String?) = repository.existsByUserName(u)

    fun currentUser() = findByEmail(authenticated()?.username)

    fun changeUserPassword(user: User, password: String?) {
        user.password = bCryptPasswordEncoder.encode(password)
        repository.save(user)
    }

    private fun authenticated(): UserDetailsImpl? {
        return try {
            SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        } catch (e: Exception) {
            null
        }
    }

}
