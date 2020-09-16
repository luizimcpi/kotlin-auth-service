package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.config.Roles
import br.com.devlhse.kotlinauthservice.domain.common.utils.Cipher
import br.com.devlhse.kotlinauthservice.domain.common.utils.JwtProvider
import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.domain.model.response.UserLoginResponse
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import br.com.devlhse.kotlinauthservice.exception.ConflictException
import br.com.devlhse.kotlinauthservice.exception.UnauthorizedException
import org.apache.logging.log4j.LogManager
import java.util.Base64

class UserService(private val cipher: Cipher,
                  private val jwtProvider: JwtProvider,
                  private val userRepository: UserRepository) {

    private val base64Encoder = Base64.getEncoder()
    private val logger = LogManager.getLogger(UserService::class.java.name)

    fun save(user: User) {

        userRepository.findByEmail(user.email)?.let {
            logger.error("Erro ao cadastrar usuário com email: ${user.email} já existente")
            throw ConflictException("Email already in use!")
        }
        userRepository.save(user.copy(password = String(base64Encoder.encode(cipher.encrypt(user.password)))))
    }

    fun authenticate(user: User): UserLoginResponse {
        val userFound = userRepository.findByEmail(user.email)
        if (userFound?.password == String(base64Encoder.encode(cipher.encrypt(user.password)))) {
            return UserLoginResponse(accessToken = generateJwtToken(userFound)!!)
        }
        logger.error("Erro ao na tentativa de login usuário com email: ${user.email}")
        throw UnauthorizedException("Invalid email or password !")
    }


    private fun generateJwtToken(user: User): String? {
        return jwtProvider.createJWT(user, Roles.AUTHENTICATED)
    }
}
