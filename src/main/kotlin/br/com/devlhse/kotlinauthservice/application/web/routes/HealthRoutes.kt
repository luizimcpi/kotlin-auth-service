package br.com.devlhse.kotlinauthservice.application.web.routes

import br.com.devlhse.kotlinauthservice.application.web.controllers.HealthCheckController
import br.com.devlhse.kotlinauthservice.config.Roles
import io.javalin.apibuilder.ApiBuilder
import io.javalin.core.security.SecurityUtil.roles

object HealthRoutes {
    fun init() {
        ApiBuilder.path("health-check") {
            ApiBuilder.get(HealthCheckController::status, roles(Roles.ANYONE))
            ApiBuilder.get("complete", HealthCheckController::statusComplete, roles(Roles.ANYONE))
        }
    }
}
