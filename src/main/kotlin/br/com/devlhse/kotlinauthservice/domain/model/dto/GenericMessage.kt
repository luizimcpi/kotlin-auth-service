package br.com.devlhse.kotlinauthservice.domain.model.dto

data class GenericMessage<T> (
    val body: T,
    override val correlationId: String
): MessageTrack
