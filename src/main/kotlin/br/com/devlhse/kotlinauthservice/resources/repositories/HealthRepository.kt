package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.repositories.DatabaseHealthRepository
import br.com.devlhse.kotlinauthservice.resources.tables.UserTable
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@SuppressWarnings("TooGenericExceptionCaught")
class HealthRepository : DatabaseHealthRepository {

    private val logger = LogManager.getLogger(HealthRepository::class.java.name)

    override fun check(): String {
       return transaction {
           try {
               UserTable.selectAll()
               STATUS_OK
           } catch (e: Exception) {
               logger.error("Error in health check repository ${e.message}")
               STATUS_ERROR
           }
       }
    }

    companion object {
        private const val STATUS_OK = "OK"
        private const val STATUS_ERROR = "ERROR"
    }
}
