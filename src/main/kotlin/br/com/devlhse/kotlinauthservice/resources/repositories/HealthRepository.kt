package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.repositories.DatabaseHealthRepository
import br.com.devlhse.kotlinauthservice.resources.tables.UserTable
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@SuppressWarnings("TooGenericExceptionCaught")
class HealthRepository : DatabaseHealthRepository {

    private val logger = LogManager.getLogger(HealthRepository::class.java.name)

    override fun status(): Boolean = transaction {
        try {
            exec("select 1")
            true
        }catch(e: Exception) {
            logger.error("Database health check error: ${e.message}")
            false
        }
    }
}
