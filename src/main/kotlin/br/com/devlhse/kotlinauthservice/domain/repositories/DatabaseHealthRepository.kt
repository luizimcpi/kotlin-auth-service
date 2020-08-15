package br.com.devlhse.kotlinauthservice.domain.repositories

interface DatabaseHealthRepository {
    fun check(): String
}
