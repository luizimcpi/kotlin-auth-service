package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.domain.model.dto.Pageable
import br.com.devlhse.kotlinauthservice.domain.services.ContactService
import io.javalin.http.Context
import org.apache.logging.log4j.LogManager
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressWarnings("TooGenericExceptionCaught")
object ContactController : KoinComponent {

    private val contactService by inject<ContactService>()
    private val logger = LogManager.getLogger(ContactController::class.java.name)

    fun findPaginatedContactsbyUser(ctx: Context) {
        val userId = ctx.pathParam("user-id").toInt()
        val pageSize = ctx.queryParam("pageSize", Int::class.java,"20").get()
        val pageNumber = ctx.queryParam("pageNumber", Long::class.java).get()
        val pageable = Pageable(pageNumber = pageNumber, pageSize = pageSize)
        ctx.json(contactService.findPaginatedContacts(userId, pageable))
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
