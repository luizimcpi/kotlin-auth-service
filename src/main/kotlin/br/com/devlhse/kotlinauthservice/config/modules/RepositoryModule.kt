package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import br.com.devlhse.kotlinauthservice.domain.repositories.DatabaseHealthRepository
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import br.com.devlhse.kotlinauthservice.resources.repositories.ContactRepositoryImpl
import br.com.devlhse.kotlinauthservice.resources.repositories.HealthRepository
import br.com.devlhse.kotlinauthservice.resources.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<DatabaseHealthRepository> { HealthRepository() }
    single<ContactRepository> { ContactRepositoryImpl() }
}
