package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.extensions.toUser
import br.com.devlhse.kotlinauthservice.domain.extensions.toUserResponse
import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.domain.model.response.UserResponse
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import br.com.devlhse.kotlinauthservice.resources.tables.UserTable
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime

class UserRepositoryImpl: UserRepository {

    private val logger = LogManager.getLogger(UserRepository::class.java.name)

    override fun findAll(): List<UserResponse> {
        var users = mutableListOf<UserResponse>()
        transaction {
            addLogger(StdOutSqlLogger)
            UserTable.selectAll().map {
                users.add(UserTable.toUserResponse(it))
            }
        }
        logger.info("Returning ${users.size} users from database")
        return users
    }


    override fun save(user: User) {
        transaction {
            addLogger(StdOutSqlLogger)
            val userId = UserTable.insertAndGetId {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
                it[createdAt] = LocalDateTime.now()
                it[updatedAt] = LocalDateTime.now()
            }

            logger.info("User has been inserted with id: $userId")
        }
    }

    override fun findById(id: Int): UserResponse? {
        return transaction {
             UserTable.select { UserTable.id eq id }.firstOrNull()?.let {
                 logger.info("User has been found with id: $id")
                 UserTable.toUserResponse(it)
             }
        }
    }

    override fun findByEmail(email: String): User? {
        return transaction {
            UserTable.select { UserTable.email eq email }.firstOrNull()?.let {
                UserTable.toUser(it)
            }
        }
    }

    override fun update(id: Int, user: User) {
        transaction {
            UserTable.update({ UserTable.id eq id }) {
                it[name] = user.name
                it[email] = user.email
                it[updatedAt] = LocalDateTime.now()
            }
        }
        logger.info("User has been updated with id: $id")
    }

    override fun delete(id: Int) {
        transaction {
            UserTable.deleteWhere { UserTable.id eq id }
        }
        logger.info("User has been deleted with id: $id")
    }
}
