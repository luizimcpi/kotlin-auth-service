package br.com.devlhse.kotlinauthservice.domain.model.response

class HealthCheckCompleteResponse (
    val packageVersion: String? = null,
    val buildDate: String? = null,
    val status: String,
    val databaseStatus: String
)
