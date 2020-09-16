package br.com.devlhse.kotlinauthservice.config.modules

import br.com.devlhse.kotlinauthservice.config.Constants.CONTACT_LISTENER
import br.com.devlhse.kotlinauthservice.config.Constants.LIST_OF_CONSUMER_MANAGER
import br.com.devlhse.kotlinauthservice.config.Constants.QUEUE_SESSION_CONTACT_SQS
import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.domain.model.request.ContactRequest
import br.com.devlhse.kotlinauthservice.domain.services.ContactListenerService
import br.com.devlhse.kotlinauthservice.resources.messaging.MessageConsumerListener
import br.com.devlhse.kotlinauthservice.resources.messaging.manager.QueueConsumerManager
import br.com.devlhse.kotlinauthservice.resources.messaging.session.QueueSession
import com.amazon.sqs.javamessaging.ProviderConfiguration
import org.koin.dsl.module


val messagingModule = module {
    single(CONTACT_LISTENER) {
        MessageConsumerListener(
            get<ContactListenerService>(),
            ContactRequest::class.java
        )
    }

    single { ProviderConfiguration() }

    single(QUEUE_SESSION_CONTACT_SQS) {
        QueueSession(
            get(),
            get<EnvironmentConfig>().contactSqsAddress,
            get<EnvironmentConfig>().contactSqsRegion
        )
    }

    single(LIST_OF_CONSUMER_MANAGER) {
        listOf(
            QueueConsumerManager<ContactRequest>(
                get(QUEUE_SESSION_CONTACT_SQS),
                get<EnvironmentConfig>().contactSqsQueueName,
                get(CONTACT_LISTENER)
            )
        )
    }
}