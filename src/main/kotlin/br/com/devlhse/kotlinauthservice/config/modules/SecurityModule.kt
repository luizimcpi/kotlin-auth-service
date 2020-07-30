package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.config.AuthConfig
import br.com.devlhse.kotlinauthservice.domain.common.utils.Cipher
import br.com.devlhse.kotlinauthservice.domain.common.utils.JwtProvider
import org.koin.dsl.module

val securityModule = module {
    single { Cipher(get()) }
    single { JwtProvider(get()) }
    single { AuthConfig(get(), get()) }
}
