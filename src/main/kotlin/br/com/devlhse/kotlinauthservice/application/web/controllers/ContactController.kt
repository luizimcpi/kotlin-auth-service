package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.config.AuthConfig
import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.services.ContactService
import br.com.devlhse.kotlinauthservice.exception.NotFoundException
import io.javalin.http.Context
import org.apache.logging.log4j.LogManager
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


//    fun createUser(ctx: Context) {
//        ctx.bodyValidator<UserRequest>()
//            .check({ it.user?.email?.isEmailValid() ?: true })
//            .check({ !it.user?.password.isNullOrBlank() })
//            .get().user?.also { user ->
//            userService.save(user)
//            ctx.status(HttpStatus.CREATED_201)
//        }
//    }
//
//    fun getUser(ctx: Context) {
//        try {
//            ctx.json(userService.findById(ctx.pathParam("user-id").toInt())!!)
//        } catch (e: Exception) {
//            logger.error("Erro ao consultar usuário pelo id $e")
//            throw NotFoundException("User Not Found")
//        }
//    }
//
//    fun updateUser(ctx: Context) {
//        ctx.bodyValidator<UserRequest>()
//            .check({ it.user?.email?.isEmailValid() ?: true })
//            .check({ !it.user?.password.isNullOrBlank() })
//            .get().user?.also { user ->
//            userService.update(ctx.pathParam("user-id").toInt(), user)
//            ctx.status(HttpStatus.CREATED_201)
//        }
//        ctx.status(HttpStatus.NO_CONTENT_204)
//    }
//
//    fun deleteUser(ctx: Context) {
//        userService.delete(ctx.pathParam("user-id").toInt())
//        ctx.status(HttpStatus.NO_CONTENT_204)
//    }
}
