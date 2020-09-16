package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.common.utils.Cipher
import br.com.devlhse.kotlinauthservice.domain.common.utils.JwtProvider
import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import br.com.devlhse.kotlinauthservice.exception.ConflictException
import br.com.devlhse.kotlinauthservice.exception.UnauthorizedException
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Base64


class UserServiceTest {

    private lateinit var cipher: Cipher
    private lateinit var jwtProvider: JwtProvider
    private lateinit var repository: UserRepository

    @BeforeEach
    fun beforeEach(){
        cipher = mockk(relaxed = true)
        jwtProvider = mockk(relaxed = true)
        repository = mockk(relaxed = true)
    }

    @AfterEach
    fun afterEach() {
        unmockkAll()
    }


    @Test
    fun `when receive valid name and email should call repository and save with success`() {
        val base64Encoder = Base64.getEncoder()
        val validEmail = "valid_email@teste.com"
        val user = User(name = "valid_name", email = validEmail, password = "valid_password")

        val userService =
            UserService(
                cipher,
                jwtProvider,
                repository
            )

        every { repository.findByEmail(validEmail) } returns null

        userService.save(user)

        verify {
            repository.save(user.copy(password = String(base64Encoder.encode(cipher.encrypt(user.password)))))
        }
    }

    @Test
    fun `when receive valid user credentials should return user with accessToken`() {
        val validEmail = "valid_email@teste.com"
        val user = User(name = "valid_name", email = validEmail, password = "valid_password")

        val userService =
            UserService(
                cipher,
                jwtProvider,
                repository
            )

        every { repository.findByEmail(user.email) } returns user

        assertNotNull{
            userService.authenticate(user)
        }
    }

    @Test
    fun `when receive invalid user credentials should throw exception`() {
        val invalidEmail = "invalid_email@teste.com"
        val user = User(name = "valid_name", email = invalidEmail, password = "valid_password")

        val userService =
            UserService(
                cipher,
                jwtProvider,
                repository
            )

        every { repository.findByEmail(user.email) } returns null

        assertThrows<UnauthorizedException> { userService.authenticate(user) }
    }

    @Test
    fun `when receive valid name and repeated email should not call repository to save`() {

        val alreadyRegisteredEmail = "valid_email@teste.com"
        val user = User(name = "valid_name", email = alreadyRegisteredEmail, password = "valid_password")
        val userService =
            UserService(
                cipher,
                jwtProvider,
                repository
            )
        val validUser = User(
            "valid_name", alreadyRegisteredEmail, "*****", "*****", 1
        )

        every { repository.findByEmail(alreadyRegisteredEmail) } returns validUser

        assertThrows<ConflictException> { userService.save(user) }
    }

}