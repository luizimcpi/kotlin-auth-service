package br.com.devlhse.kotlinauthservice.domain.model.entity

data class Contact (
    val name: String,
    val email: String,
    val phone: String,
    val id: Int? = null,
    val userId: Int? = null
)
