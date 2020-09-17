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
import org.koin.core.qualifier.named
import org.koin.dsl.module


val messagingModule = module {
    single(named(CONTACT_LISTENER)) {
        MessageConsumerListener(
            get<ContactListenerService>(),
            ContactRequest::class.java
        )
    }

    single { ProviderConfiguration() }

    single(named(QUEUE_SESSION_CONTACT_SQS)) {
        QueueSession(
            get(),
            get<EnvironmentConfig>().contactSqsAddress,
            get<EnvironmentConfig>().contactSqsRegion
        )
    }

    single(named(LIST_OF_CONSUMER_MANAGER)) {
        listOf(
            QueueConsumerManager<ContactRequest>(
                get(named(QUEUE_SESSION_CONTACT_SQS)),
                get<EnvironmentConfig>().contactSqsQueueName,
                get(named(CONTACT_LISTENER))
            )
        )
    }
}
