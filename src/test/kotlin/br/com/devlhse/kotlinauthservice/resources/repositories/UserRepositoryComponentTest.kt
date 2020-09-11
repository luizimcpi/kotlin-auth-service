package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.PostgresMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest

@ExtendWith(ComponentTestExtension::class)
class UserRepositoryComponentTest: KoinTest {

    private val repository =  UserRepository()

    @Test
    fun `when findall should return users with success`() {
        val path = "find_all_users"
        PostgresMock.executeScripts("$path/001.sql")
        assertEquals(2, repository.findAll().size)
    }

    @Test
    fun `when findById should return user with success`() {
        val path = "find_by_id"
        val validId = 2L
        PostgresMock.executeScripts("$path/001.sql")
        val user = repository.findById(validId)
        assertEquals(validId, user!!.id)
    }

    @Test
    fun `when save an user should not throw exception`() {
        val validUser = User("User", "user@email.com", "teste1234")
        assertDoesNotThrow {
            repository.save(validUser)
        }
    }

    @Test
    fun `when findByEmail should return user with success`() {
        val path = "find_by_email"
        val validEmail = "user4@gmail.com"
        PostgresMock.executeScripts("$path/001.sql")
        val user = repository.findByEmail(validEmail)
        assertEquals(validEmail, user!!.email)
    }

    @Test
    fun `when delete an user should does not throw exception`() {
        val path = "delete"
        val validId = 2L
        PostgresMock.executeScripts("$path/001.sql")
        assertDoesNotThrow {
            repository.delete(validId)
        }
        val userExists = repository.findAll().any { user -> user.id == validId }
        assertFalse(userExists)
    }
}
