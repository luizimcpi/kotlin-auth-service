package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.config.ObjectMapperConfig
import org.koin.dsl.module

val configModule = module {
    single { EnvironmentConfig() }
    single { ObjectMapperConfig.jsonObjectMapper }
}
