package br.com.devlhse.kotlinauthservice.domain.gateway

import br.com.devlhse.kotlinauthservice.domain.model.response.AddressResponse

interface AddressGateway {
    fun getAddressByCep(cep: String): AddressResponse
}
