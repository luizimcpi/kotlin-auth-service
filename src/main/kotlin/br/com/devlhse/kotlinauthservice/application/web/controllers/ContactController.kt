package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.config.AuthConfig
import br.com.devlhse.kotlinauthservice.domain.extensions.isEmailValid
import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.model.request.ContactRequest
import br.com.devlhse.kotlinauthservice.domain.services.ContactService
import br.com.devlhse.kotlinauthservice.exception.NotFoundException
import io.javalin.http.Context
import org.apache.logging.log4j.LogManager
import org.eclipse.jetty.http.HttpStatus
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressWarnings("TooGenericExceptionCaught")
object ContactController : KoinComponent {

    private val authConfig by inject<AuthConfig>()
    private val contactService by inject<ContactService>()
    private val logger = LogManager.getLogger(ContactController::class.java.name)

    fun findPaginatedContactsbyUser(ctx: Context) {
        val pageSize = ctx.queryParam("pageSize", Int::class.java,"20").get()
        val pageNumber = ctx.queryParam("pageNumber", Long::class.java).get()
        val pageable = Pageable(pageNumber = pageNumber, pageSize = pageSize)
        val decodedJwt = authConfig.getJwtTokenHeader(ctx)

        try {
            authConfig.getEmail(decodedJwt)?.let { recoveredEmail ->
                logger.info(
                    "Start Get Paginated contacts of user with email $recoveredEmail " +
                            "with max itens per page: $pageSize and pageNumber: $pageNumber "
                )
                ctx.json(contactService.findPaginatedContacts(recoveredEmail, pageable))
            }
        }catch(e: Exception) {
            logger.error("Erro ao consultar contatos do usuario $e")
            throw NotFoundException("User contacts Not Found")
        }
    }


    fun createContact(ctx: Context) {
        val decodedJwt = authConfig.getJwtTokenHeader(ctx)

        try {
        ctx.bodyValidator<ContactRequest>()
            .check({ it.contact?.email?.isEmailValid() ?: true })
            .check({ !it.contact?.phone.isNullOrBlank() })
            .check({ !it.contact?.name.isNullOrBlank() })
            .get().contact?.also { contact ->
                authConfig.getEmail(decodedJwt)?.let{ recoveredEmail ->
                    contactService.save(recoveredEmail, contact)
                    ctx.status(HttpStatus.CREATED_201)
                }
            }
        }catch(e: Exception) {
            logger.error("Erro ao incluir contato do usuario $e")
            throw NotFoundException("Contact insertion error")
        }
    }

    fun getContactById(ctx: Context) {
        val decodedJwt = authConfig.getJwtTokenHeader(ctx)
        try {
            authConfig.getEmail(decodedJwt)?.let { recoveredEmail ->
                ctx.json(contactService.findById(recoveredEmail, ctx.pathParam("contact-id").toInt())!!)
            }
        } catch (e: Exception) {
            logger.error("Erro ao consultar contato pelo id $e")
            throw NotFoundException("Contact Not Found")
        }
    }

    fun updateContact(ctx: Context) {
        val decodedJwt = authConfig.getJwtTokenHeader(ctx)
        try {
            ctx.bodyValidator<ContactRequest>()
                .check({ it.contact?.email?.isEmailValid() ?: true })
                .check({ !it.contact?.phone.isNullOrBlank() })
                .check({ !it.contact?.name.isNullOrBlank() })
                .get().contact?.also { contact ->
                    authConfig.getEmail(decodedJwt)?.let { recoveredEmail ->
                        contactService.update(recoveredEmail, ctx.pathParam("contact-id").toInt(), contact)
                        ctx.status(HttpStatus.NO_CONTENT_204)
                    }
                }
        } catch(e: Exception) {
            logger.error("Erro ao atualizar contato do usuario $e")
            throw NotFoundException("Contact update error")
        }
    }

    fun deleteContact(ctx: Context) {
        val decodedJwt = authConfig.getJwtTokenHeader(ctx)
        try{
            authConfig.getEmail(decodedJwt)?.let { recoveredEmail ->
                contactService.delete(recoveredEmail, ctx.pathParam("contact-id").toInt())
                ctx.status(HttpStatus.NO_CONTENT_204)
            }
        }catch(e: Exception) {
            logger.error("Erro ao exlcuir contato do usuario $e")
            throw NotFoundException("Contact delete error")
        }
    }
}
