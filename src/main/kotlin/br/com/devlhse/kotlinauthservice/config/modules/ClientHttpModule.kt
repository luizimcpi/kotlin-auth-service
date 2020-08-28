package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.domain.gateway.AddressGateway
import br.com.devlhse.kotlinauthservice.resources.clients.ViaCepClient
import org.koin.dsl.module

val clientHttpModule = module {
    single<AddressGateway> { ViaCepClient(EnvironmentConfig().viaCepUrl) }
}
