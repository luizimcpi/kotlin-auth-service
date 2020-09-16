package br.com.devlhse.kotlinauthservice.domain.services

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.entity.Contact
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactPageable
import br.com.devlhse.kotlinauthservice.domain.model.response.ContactResponse
import br.com.devlhse.kotlinauthservice.domain.repositories.ContactRepository
import br.com.devlhse.kotlinauthservice.domain.repositories.UserRepository
import br.com.devlhse.kotlinauthservice.exception.NotFoundException
import org.apache.logging.log4j.LogManager

class ContactService(private val userRepository: UserRepository,
                     private val contactRepository: ContactRepository) {

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

    fun save(userRecoveredEmail: String, contact: Contact) {
        userRepository.findByEmail(userRecoveredEmail)?.let { user ->
            contactRepository.save(contact.copy(userId = user.id))
        }
    }

    fun findById(userRecoveredEmail: String, id: Int): ContactResponse? {
        userRepository.findByEmail(userRecoveredEmail)?.let { user ->
            return contactRepository.findById(user.id!!, id)
        }
        throw NotFoundException("Error when find contact with id: $id")
    }

    fun update(userRecoveredEmail: String, id: Int, contact: Contact) {
        userRepository.findByEmail(userRecoveredEmail)?.let { user ->
            contactRepository.update(id, user.id!!, contact)
        }
    }

    fun delete(userRecoveredEmail: String, id: Int) {
        userRepository.findByEmail(userRecoveredEmail)?.let { user ->
            contactRepository.delete(id, user.id!!)
        }
    }
}
