package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactPageable
import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import br.com.devlhse.kotlinauthservice.resources.tables.ContactTable
import br.com.devlhse.kotlinauthservice.resources.tables.ContactTable.email
import br.com.devlhse.kotlinauthservice.resources.tables.ContactTable.name
import br.com.devlhse.kotlinauthservice.resources.tables.ContactTable.phone
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.ceil

@SuppressWarnings("TooGenericExceptionCaught")
class ContactRepositoryImpl: ContactRepository {

    private val logger = LogManager.getLogger(ContactRepositoryImpl::class.java.name)

    override fun findByUser(userId: Int, pageable: Pageable): ContactPageable {
        val query = ContactTable.select { ContactTable.userId eq userId }

        val totalPages = countTotalPages(query, pageable.pageSize)

        val selectOrderBy = query.orderBy(ContactTable.id)

        val contacts = if( totalPages > 0 ) transaction { findBy(selectOrderBy, pageable) } else emptyList()

        return ContactPageable(
            contacts, pageable.copy(totalPages = totalPages)
        )

    }

    private fun findBy(query: Query, pageable: Pageable): List<Contact> = try {
        transaction {
            addLogger(StdOutSqlLogger)
            findByPage(
                query,
                pageable.pageSize,
                pageable.pageNumber
            )
        }.map { it.toEntity() }
    } catch (ex: Exception) {
        logger.error("error in page: ${pageable.pageNumber} message: ${ex.message}")
        emptyList()
    }

    private fun findByPage(query: Query, pageSize: Int, page: Long) = query.limit(pageSize, (page - 1L) * pageSize)

    private fun countTotalPages(query: Query, pageSize: Int) = try {
        val count = transaction { query.count() }.toDouble()

        ceil(count.div(pageSize)).toLong()
    } catch (ex: Exception) {
        logger.error("error in counter: ${ex.message}")
        throw ex
    }
}


private fun ResultRow.toEntity() = Contact(
    name = this[name],
    email = this[email],
    phone = this[phone]
)
