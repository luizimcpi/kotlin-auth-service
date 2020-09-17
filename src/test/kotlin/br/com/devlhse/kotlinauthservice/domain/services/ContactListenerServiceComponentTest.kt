package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.stubs.SqsStub
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(ComponentTestExtension::class)
class ContactListenerServiceComponentTest {

    @Test
    fun `when execute receive empty messages should not response errors`() {
        val result = SqsStub.receiveMessages(EnvironmentConfig().contactSqsQueueName)
        val messages = result.messages
        assertEquals(0, messages.size)
    }
}