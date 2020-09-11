package br.com.devlhse.kotlinauthservice.application.web

import br.com.devlhse.kotlinauthservice.application.web.routes.AddressRoutes
import br.com.devlhse.kotlinauthservice.application.web.routes.HealthRoutes
import br.com.devlhse.kotlinauthservice.application.web.routes.UserRoutes
import br.com.devlhse.kotlinauthservice.config.AuthConfig
import br.com.devlhse.kotlinauthservice.config.DatabaseConfig
import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.config.ErrorExceptionMapping
import br.com.devlhse.kotlinauthservice.config.ObjectMapperConfig
import br.com.devlhse.kotlinauthservice.config.modules.clientHttpModule
import br.com.devlhse.kotlinauthservice.config.modules.configModule
import br.com.devlhse.kotlinauthservice.config.modules.repositoryModule
import br.com.devlhse.kotlinauthservice.config.modules.securityModule
import br.com.devlhse.kotlinauthservice.config.modules.serviceModule
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

object AuthServiceEntryPoint : KoinComponent {
    private val environmentConfig: EnvironmentConfig by inject()
    private val authConfig: AuthConfig by inject()
    private lateinit var app: Javalin

    fun init() {
        startKoin {
            // use Koin logger
            printLogger()
            // declare modules
            modules(listOf(securityModule, configModule, serviceModule, repositoryModule, clientHttpModule))
        }

        app = Javalin.create {
            if (EnvironmentConfig().enableDebug) {
                it.enableDevLogging()
            }
        }.apply {
            JavalinJackson.configure(ObjectMapperConfig.jsonObjectMapper)
        }.start(EnvironmentConfig().serverPort)

        authConfig.configure(app)
        ErrorExceptionMapping.register(app)
        DatabaseConfig(environmentConfig).connect()

//        transaction {
//            val generatedUserId = UserTable.insertAndGetId {
//                it[name] = "User teste"
//                it[email] = "teste@teste.com.br"
//                it[password] = "teste1234"
//                it[createdAt] = LocalDateTime.now()
//                it[updatedAt] = LocalDateTime.now()
//            }
//
//            val contactId = ContactTable.insertAndGetId {
//                it[name] = "teste contact"
//                it[email] = "teste@teste.com.br"
//                it[phone] = "551399177777"
//                it[userId] = generatedUserId.value
//                it[UserTable.createdAt] = LocalDateTime.now()
//                it[UserTable.updatedAt] = LocalDateTime.now()
//            }
//        }

        app.routes {
            UserRoutes.init()
            HealthRoutes.init()
            AddressRoutes.init()
        }
    }

    fun stop() {
        app.stop()
    }
}
