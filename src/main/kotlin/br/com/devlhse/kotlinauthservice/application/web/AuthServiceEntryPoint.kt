package br.com.devlhse.kotlinauthservice.application.web

import br.com.devlhse.kotlinauthservice.application.web.routes.AddressRoutes
import br.com.devlhse.kotlinauthservice.application.web.routes.ContactRoutes
import br.com.devlhse.kotlinauthservice.application.web.routes.HealthRoutes
import br.com.devlhse.kotlinauthservice.application.web.routes.UserRoutes
import br.com.devlhse.kotlinauthservice.config.AuthConfig
import br.com.devlhse.kotlinauthservice.config.Constants.LIST_OF_CONSUMER_MANAGER
import br.com.devlhse.kotlinauthservice.config.DatabaseConfig
import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.config.ErrorExceptionMapping
import br.com.devlhse.kotlinauthservice.config.ObjectMapperConfig
import br.com.devlhse.kotlinauthservice.config.modules.clientHttpModule
import br.com.devlhse.kotlinauthservice.config.modules.configModule
import br.com.devlhse.kotlinauthservice.config.modules.messagingModule
import br.com.devlhse.kotlinauthservice.config.modules.repositoryModule
import br.com.devlhse.kotlinauthservice.config.modules.securityModule
import br.com.devlhse.kotlinauthservice.config.modules.serviceModule
import br.com.devlhse.kotlinauthservice.domain.model.dto.MessageTrack
import br.com.devlhse.kotlinauthservice.resources.messaging.manager.QueueConsumerManager
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.apache.logging.log4j.LogManager
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.core.qualifier.named

@SuppressWarnings("TooGenericExceptionCaught")
object AuthServiceEntryPoint : KoinComponent {
    private val environmentConfig: EnvironmentConfig by inject()
    private val authConfig: AuthConfig by inject()
    private val listQueueConsumerManager: List<QueueConsumerManager<MessageTrack>>
            by inject(named(LIST_OF_CONSUMER_MANAGER))
    private lateinit var app: Javalin

    private val logger = LogManager.getLogger(AuthServiceEntryPoint::class.java.name)

    fun init() {
        startKoin {
            // use Koin logger
            printLogger()
            // declare modules
            modules(
                listOf(
                    securityModule,
                    configModule,
                    serviceModule,
                    repositoryModule,
                    clientHttpModule,
                    messagingModule
                )
            )
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


        app.routes {
            UserRoutes.init()
            HealthRoutes.init()
            AddressRoutes.init()
            ContactRoutes.init()
        }
    }

    fun initConsumers (){
        try{
            listQueueConsumerManager.forEach { it.startConsumer() }
        }catch (e: Exception){
            logger.error("unable to connect to sqs due to: ${e.message}")
            throw e
        }
    }

    fun stop() {
        app.stop()
    }
}
