package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.domain.extensions.isEmailValid
import br.com.devlhse.kotlinauthservice.domain.model.request.UserRequest
import br.com.devlhse.kotlinauthservice.domain.services.UserService
import io.javalin.http.Context
import org.apache.logging.log4j.LogManager
import org.eclipse.jetty.http.HttpStatus
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressWarnings("TooGenericExceptionCaught")
object UserController : KoinComponent {

    private val userService by inject<UserService>()
    private val logger = LogManager.getLogger(UserController::class.java.name)


    fun login(ctx: Context) {
        ctx.bodyValidator<UserRequest>()
            .check({ it.user?.email?.isEmailValid() ?: true })
            .check({ !it.user?.password.isNullOrBlank() })
            .get().user?.also { user ->
            userService.authenticate(user).apply {
                ctx.json(this)
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
}
