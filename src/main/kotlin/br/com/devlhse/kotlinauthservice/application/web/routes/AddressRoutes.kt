package br.com.devlhse.kotlinauthservice.application.web.routes

import br.com.devlhse.kotlinauthservice.application.web.controllers.AddressController
import br.com.devlhse.kotlinauthservice.config.Roles
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.security.SecurityUtil.roles

object AddressRoutes {
    fun init() {
        ApiBuilder.path("addresses") {
            ApiBuilder.path(":cep") {
                ApiBuilder.get(AddressController::getAddressByCep, roles(Roles.ANYONE))
            }
        }
    }
}
