package br.com.devlhse.kotlinauthservice.domain.model.entity

data class User(
    val name: String,
    val email: String,
    val password: String,
    val token: String? = null,
    val id: Int? = null
)
