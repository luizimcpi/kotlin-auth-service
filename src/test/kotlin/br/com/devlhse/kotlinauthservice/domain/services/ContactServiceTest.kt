package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact
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

    @Test
    fun `when create contact should call repository to save new user contact`() {

        val contactService =
            ContactService(
                userRepository,
                repository
            )


        val userRecoveredEmail = "teste@teste.com"
        val validUser = User("teste", userRecoveredEmail, "teste1234", "123456")
        val validContact = Contact("teste", "teste@teste.com.br", "5513999999999")

        every{ userRepository.findByEmail(userRecoveredEmail) } returns validUser

        contactService.save(userRecoveredEmail, validContact)

        verify {
            repository.save(validContact)
        }
    }

    @Test
    fun `when find contact by id should call repository to find an user contact`() {

        val contactService =
            ContactService(
                userRepository,
                repository
            )


        val userRecoveredEmail = "teste@teste.com"
        val validUser = User("teste", userRecoveredEmail, "teste1234", "123456", 1)

        every{ userRepository.findByEmail(userRecoveredEmail) } returns validUser

        contactService.findById(userRecoveredEmail, 1)

        verify {
            repository.findById(1, 1)
        }
    }

}