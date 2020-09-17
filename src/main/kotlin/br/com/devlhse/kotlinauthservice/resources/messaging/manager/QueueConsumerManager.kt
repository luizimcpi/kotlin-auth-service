package br.com.devlhse.kotlinauthservice.resources.messaging.manager

import br.com.devlhse.kotlinauthservice.resources.messaging.MessageConsumerListener
import br.com.devlhse.kotlinauthservice.resources.messaging.session.QueueSession
import org.apache.logging.log4j.LogManager
import javax.jms.MessageConsumer

@SuppressWarnings("TooGenericExceptionCaught")
class QueueConsumerManager<T: Any>(
    private val queueSession: QueueSession,
    private val queueName: String,
    private val listener: MessageConsumerListener<T>
) : QueueManager() {

    private val logger = LogManager.getLogger(QueueConsumerManager::class.java.name)

    private val consumer by lazy { createMessageConsumer() }

    fun startConsumer(){
        consumer.messageListener = listener
        queueSession.start()
    }

    private fun createMessageConsumer(): MessageConsumer {
        try {
            val session = queueSession.session
            val queue = createQueue(session, queueName)
            return session.createConsumer(queue)
        }catch (e: Exception) {
            logger.error("could not create consumer queue: ${e.message}")
            throw e
        }
    }
}
