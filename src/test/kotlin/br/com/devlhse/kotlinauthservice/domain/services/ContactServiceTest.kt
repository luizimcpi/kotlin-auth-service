package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ContactServiceTest {

    private lateinit var repository: ContactRepository

    @BeforeEach
    fun beforeEach(){
        repository = mockk(relaxed = true)
    }

    @AfterEach
    fun afterEach() {
        unmockkAll()
    }

    @Test
    fun `when execute find paginated contacts should call repository to find all user contacts`() {

        val contactService =
            ContactService(
                repository
            )

        val validPageable = Pageable(1,5)

        contactService.findPaginatedContacts(1, validPageable)

        verify {
            repository.findByUser(1, validPageable)
        }
    }

}