package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.GenericMessage
import br.com.devlhse.kotlinauthservice.domain.model.request.ContactRequest
import org.apache.logging.log4j.LogManager

class ContactListenerService (
    private val contactService: ContactService
): ListenerService<ContactRequest>{

    private val logger = LogManager.getLogger(ContactListenerService::class.java.name)

    override fun process(genericMessage: GenericMessage<ContactRequest>) {
        val body = genericMessage.body

        logger.info("processing contact: ${body.contact}")

        //change this to receive variable email
        contactService.save("user1@gmail.com", body.contact!!)

        logger.info("processed contact: ${body.contact}")
    }
}
