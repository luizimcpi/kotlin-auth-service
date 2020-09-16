package br.com.devlhse.kotlinauthservice.resources.messaging

import br.com.devlhse.kotlinauthservice.config.ObjectMapperConfig
import br.com.devlhse.kotlinauthservice.domain.model.dto.GenericMessage
import br.com.devlhse.kotlinauthservice.domain.services.ListenerService
import de.huxhorn.sulky.ulid.ULID
import org.apache.logging.log4j.LogManager
import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.TextMessage

class MessageConsumerListener<T> (
    private val listenerService: ListenerService<T>,
    private val valueType: Class<T>
) : MessageListener {

    private val logger = LogManager.getLogger(MessageConsumerListener::class.java.name)

    override fun onMessage(message: Message) {
        try {
            val rawtext = message as TextMessage
            val dtoGeneric = ObjectMapperConfig.jsonObjectMapper.readValue(rawtext.text, valueType)
            //message.jmsCorrelationID
            val consumedMessage = GenericMessage(dtoGeneric, ULID().nextULID())
            listenerService.process(consumedMessage)
            message.acknowledge()
        }catch (e: Exception){
            logger.error("error on generic listener: ${e.message}")
        }
    }
}
