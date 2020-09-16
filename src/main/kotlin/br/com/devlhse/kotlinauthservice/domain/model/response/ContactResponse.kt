package br.com.devlhse.kotlinauthservice.domain.model.response

import java.time.LocalDateTime

data class ContactResponse(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
