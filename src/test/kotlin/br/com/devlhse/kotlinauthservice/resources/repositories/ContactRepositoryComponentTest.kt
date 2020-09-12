package br.com.devlhse.kotlinauthservice.resources.repositories

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.utils.ComponentTestExtension
import br.com.devlhse.kotlinauthservice.utils.PostgresMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest

@ExtendWith(ComponentTestExtension::class)
class ContactRepositoryComponentTest: KoinTest {

    private val repository =  ContactRepositoryImpl()

    @Test
    fun `when findByUser should return paginated contacts of an user with success`() {
        val path = "find_paginated_contacts"
        val validPageable = Pageable(1, 5)
        PostgresMock.executeScripts("$path/001.sql")

        repository.findByUser(1, pageable = validPageable).also {
            assertEquals(5, it.contacts.size)
            assertEquals(2, it.pageable.totalPages)
        }
    }
}