package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.domain.repositories.DatabaseHealthRepository
import br.com.devlhse.kotlinauthservice.resources.repositories.HealthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository> {
        br.com.devlhse.kotlinauthservice.resources.repositories.UserRepository()
    }
    single<DatabaseHealthRepository> { HealthRepository() }
}
