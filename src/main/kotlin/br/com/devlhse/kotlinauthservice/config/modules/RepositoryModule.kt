package br.com.devlhse.kotlinauthservice.config.modules

import org.koin.dsl.module

val repositoryModule = module {
    single<br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository> {
        br.com.devlhse.kotlinauthservice.resources.repositories.UserRepository()
    }
}
