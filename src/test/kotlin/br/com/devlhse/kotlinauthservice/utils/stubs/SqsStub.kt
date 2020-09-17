package br.com.devlhse.kotlinauthservice.utils.stubs

import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.utils.TestUtils
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.ReceiveMessageResult
import org.awaitility.kotlin.await
import org.awaitility.kotlin.matches
import org.awaitility.kotlin.untilCallTo
import org.elasticmq.NodeAddress
import org.elasticmq.rest.sqs.SQSRestServerBuilder

object SqsStub {
    private val port = TestUtils.port(EnvironmentConfig().contactSqsAddress)

    private val fakeCredentialsProvider = object : AWSCredentialsProvider {
        override fun getCredentials() = BasicAWSCredentials("x", "x")
        override fun refresh() {}
    }

    private val sqsServer = SQSRestServerBuilder
        .withPort(port)
        .withServerAddress(NodeAddress("http", "localhost", port, ""))

    private val sqsClient = AmazonSQSClientBuilder.standard()
        .withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration("http://localhost:$port", "us-east-1")
        ).withCredentials(fakeCredentialsProvider).build()

    private val queueUrls = mutableMapOf<String, String>()

    fun createQueues(stubbedQueues: List<String>){
        stubbedQueues.map {
            if(!queueUrls.containsKey(it)){
                val createdQueue = sqsClient.createQueue(it)
                queueUrls[it] = createdQueue.queueUrl
            }
        }
    }

    init {
        sqsServer.start()
    }

    fun shutdown(){
        sqsClient.shutdown()
    }

    fun receiveMessages(queueName: String): ReceiveMessageResult = await.untilCallTo {
        sqsClient.receiveMessage(queueUrls[queueName])
    }.matches {
        it != null
    }!!

    fun sendMessage(queueName: String, payload: String) = sqsClient.sendMessage(queueUrls[queueName], payload)

}