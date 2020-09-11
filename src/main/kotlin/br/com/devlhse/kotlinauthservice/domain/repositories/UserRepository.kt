package br.com.devlhse.kotlinauthservice.domain.repositories

import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.domain.model.response.UserResponse

interface UserRepository {
    fun findAll(): List<UserResponse>
    fun save(user: User)
    fun findById(id: Long): UserResponse?
    fun findByEmail(email: String): User?
    fun update(id: Long, user: User)
    fun delete(id: Long)
}
