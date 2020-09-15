package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactPageable
import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import br.com.devlhse.kotlinauthservice.exception.NotFoundException
import org.apache.logging.log4j.LogManager

class ContactService(private val userRepository: UserRepository, private val contactRepository: ContactRepository) {

    private val logger = LogManager.getLogger(ContactService::class.java.name)

    fun findPaginatedContacts(userRecoveredEmail: String, pageable: Pageable): ContactPageable {
        logger.info("Iniciando busca de contatos do usuario: $userRecoveredEmail com paginação: " +
                "${pageable.pageSize} e ${pageable.pageNumber}")
        userRepository.findByEmail(userRecoveredEmail)?.let { user ->
            return contactRepository.findByUser(user.id!!, pageable)
        }
        logger.error("Não foi possivel realizar a busca de contatos do usuario: $userRecoveredEmail ")
        throw NotFoundException("Contacts not found")
    }

//    fun save(user: User) {
//
//        userRepository.findByEmail(user.email)?.let {
//            logger.error("Erro ao cadastrar usuário com email: ${user.email} já existente")
//            throw ConflictException("Email already in use!")
//        }
//        userRepository.save(user.copy(password = String(base64Encoder.encode(cipher.encrypt(user.password)))))
//    }
//
//    fun authenticate(user: User): UserLoginResponse {
//        val userFound = userRepository.findByEmail(user.email)
//        if (userFound?.password == String(base64Encoder.encode(cipher.encrypt(user.password)))) {
//            return UserLoginResponse(accessToken = generateJwtToken(userFound)!!)
//        }
//        logger.error("Erro ao na tentativa de login usuário com email: ${user.email}")
//        throw UnauthorizedException("Invalid email or password !")
//    }
//
//    fun findById(id: Int): UserResponse? {
//        return userRepository.findById(id)
//    }
//
//    fun update(id: Int, user: User) {
//        userRepository.update(id, user)
//    }
//
//    fun delete(id: Int) {
//        userRepository.delete(id)
//    }
}
