package br.com.devlhse.kotlinauthservice.resources.messaging.session

import com.amazon.sqs.javamessaging.ProviderConfiguration
import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import javax.jms.Connection
import javax.jms.Session

class QueueSession(
    private val providerConfiguration: ProviderConfiguration,
    private val url: String,
    private val region: String
) {
    private val connection: Connection by lazy { createConnection() }

    val session: Session by lazy { createSession() }

    fun start() = connection.start()

    private fun createConnection() =
        SQSConnectionFactory(providerConfiguration, getEndpointConfiguration()).createConnection()

    private fun getEndpointConfiguration(): AmazonSQSClientBuilder =
        AmazonSQSClientBuilder.standard().withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration(
                url,
                region
            )
        )

    private fun createSession() = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE)
}
