package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.PostgresMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
        val validId = 2
        PostgresMock.executeScripts("$path/001.sql")
        val user = repository.findById(validId)
        assertEquals(validId, user!!.id)
    }
}