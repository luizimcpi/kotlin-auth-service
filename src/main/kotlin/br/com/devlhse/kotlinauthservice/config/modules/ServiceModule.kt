package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.domain.services.UserService
import org.koin.dsl.module

val serviceModule = module {
    single {
        UserService(
            get(),
            get(),
            get()
        )
    }
}