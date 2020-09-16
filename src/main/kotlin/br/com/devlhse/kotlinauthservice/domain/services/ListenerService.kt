package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.GenericMessage

interface ListenerService<T> {
    fun process(genericMessage: GenericMessage<T>)
}