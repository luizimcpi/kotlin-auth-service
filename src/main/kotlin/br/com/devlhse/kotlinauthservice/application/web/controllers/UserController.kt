package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.domain.extensions.isEmailValid
import br.com.devlhse.kotlinauthservice.domain.model.request.UserRequest
import br.com.devlhse.kotlinauthservice.domain.services.UserService
import br.com.devlhse.kotlinauthservice.exception.NotFoundException
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressWarnings("TooGenericExceptionCaught")
object UserController : KoinComponent {

    private val userService by inject<UserService>()

    fun getAllUsers(ctx: Context) {
        ctx.json(userService.findAll())
    }

    fun login(ctx: Context) {
        ctx.bodyValidator<UserRequest>()
            .check({ it.user?.email?.isEmailValid() ?: true })
            .check({ !it.user?.password.isNullOrBlank() })
            .get().user?.also { user ->
            userService.authenticate(user).apply {
                ctx.json(UserRequest(this))
            }
        }
    }

    fun createUser(ctx: Context) {
        ctx.bodyValidator<UserRequest>()
            .check({ it.user?.email?.isEmailValid() ?: true })
            .check({ !it.user?.password.isNullOrBlank() })
            .get().user?.also { user ->
            userService.save(user)
            ctx.status(HttpStatus.CREATED_201)
        }
    }

    fun getUser(ctx: Context) {
        try {
            ctx.json(userService.findById(ctx.pathParam("user-id").toInt())!!)
        } catch (e: Exception) {
            throw NotFoundException("User Not Found")
        }
    }

    fun updateUser(ctx: Context) {
        ctx.bodyValidator<UserRequest>()
            .check({ it.user?.email?.isEmailValid() ?: true })
            .check({ !it.user?.password.isNullOrBlank() })
            .get().user?.also { user ->
            userService.update(ctx.pathParam("user-id").toInt(), user)
            ctx.status(HttpStatus.CREATED_201)
        }
        ctx.status(HttpStatus.NO_CONTENT_204)
    }

    fun deleteUser(ctx: Context) {
        userService.delete(ctx.pathParam("user-id").toInt())
        ctx.status(HttpStatus.NO_CONTENT_204)
    }
}
