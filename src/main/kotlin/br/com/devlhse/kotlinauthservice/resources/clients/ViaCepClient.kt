package br.com.devlhse.kotlinauthservice.resources.clients

import br.com.devlhse.kotlinauthservice.domain.gateway.AddressGateway
import br.com.devlhse.kotlinauthservice.domain.model.response.AddressResponse
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.responseObject

class ViaCepClient(private val viaCepUrl: String) : AddressGateway {
    override fun getAddressByCep(cep: String): AddressResponse {
        val baseUrl = viaCepUrl
        val url = "$baseUrl$cep/json"
        return Fuel.get(url).responseObject<AddressResponse>().third.get()
    }
}
