package br.com.devlhse.kotlinauthservice.resources.messaging.manager

import javax.jms.Queue
import javax.jms.Session

abstract class QueueManager {
    protected fun createQueue(session: Session, queueName: String): Queue = session.createQueue(queueName)
}