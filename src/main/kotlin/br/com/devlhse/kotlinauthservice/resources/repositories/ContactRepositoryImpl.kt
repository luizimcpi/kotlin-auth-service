package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.extensions.toContactResponse
import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactPageable
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactResponse
import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import br.com.devlhse.kotlinauthservice.resources.tables.ContactTable
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
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

    private fun findBy(query: Query, pageable: Pageable): List<ContactResponse> = try {
        transaction {
            addLogger(StdOutSqlLogger)
            findByPage(
                query,
                pageable.pageSize,
                pageable.pageNumber
            )
        }.map { ContactTable.toContactResponse(it) }
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

    override fun save(contact: Contact) {
        transaction {
            addLogger(StdOutSqlLogger)
            val contactId = ContactTable.insertAndGetId {
                it[name] = contact.name
                it[email] = contact.email
                it[phone] = contact.phone
                it[createdAt] = LocalDateTime.now()
                it[updatedAt] = LocalDateTime.now()
                it[userId] = contact.userId!!
            }

            logger.info("Contact has been inserted with id: $contactId")
        }
    }

    override fun findById(userId: Int, id: Int): ContactResponse? {
        return transaction {
            ContactTable.select {
                (ContactTable.id eq id) and (ContactTable.userId eq userId)
            }.firstOrNull()?.let {
                logger.info("Contact has been found with id: $id")
                ContactTable.toContactResponse(it)
            }
        }
    }

    override fun update(id: Int, userId: Int, contact: Contact) {
        transaction {
            ContactTable.update({
                (ContactTable.id eq id) and (ContactTable.userId eq userId)
            }) {
                it[name] = contact.name
                it[email] = contact.email
                it[phone] = contact.phone
                it[updatedAt] = LocalDateTime.now()
            }
        }
        logger.info("Contact with id: $id has been updated")
    }

    override fun delete(id: Int, userId: Int) {
        transaction {
            ContactTable.deleteWhere { (ContactTable.id eq id) and (ContactTable.userId eq userId) }
        }
        logger.info("Contact with id: $id has been deleted")
    }
}
