package br.com.devlhse.kotlinauthservice.domain.model.response

@SuppressWarnings("LongParameterList")
class AddressResponse(
    val cep: String? = "",
    val logradouro: String? = "",
    val complemento: String? = "",
    val bairro: String? = "",
    val localidade: String? = "",
    val uf: String? = "",
    val ibge: String? = "",
    val gia: String? = "",
    val ddd: String? = ""
)
