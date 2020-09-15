package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ContactServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var repository: ContactRepository

    @BeforeEach
    fun beforeEach(){
        userRepository = mockk(relaxed = true)
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
                userRepository,
                repository
            )


        val userRecoveredEmail = "teste@teste.com"
        val validUser = User("teste", userRecoveredEmail, "teste1234", "123456", 1)
        val validPageable = Pageable(1,5)

        every{ userRepository.findByEmail(userRecoveredEmail) } returns validUser

        contactService.findPaginatedContacts(userRecoveredEmail, validPageable)

        verify {
            repository.findByUser(1, validPageable)
        }
    }

}