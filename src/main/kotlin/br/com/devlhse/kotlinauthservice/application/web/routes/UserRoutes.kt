package br.com.devlhse.kotlinauthservice.application.web.routes

import br.com.devlhse.kotlinauthservice.application.web.controllers.UserController
import br.com.devlhse.kotlinauthservice.config.Roles
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.security.SecurityUtil.roles

object UserRoutes {
    fun init() {
        ApiBuilder.path("users") {
            ApiBuilder.post(UserController::createUser, roles(Roles.ANYONE))
            ApiBuilder.post("login", UserController::login, roles(Roles.ANYONE))
        }
    }
}
