package br.com.devlhse.kotlinauthservice.domain.model.response

import java.time.LocalDateTime

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val password: String? = null,
    val accessToken: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
