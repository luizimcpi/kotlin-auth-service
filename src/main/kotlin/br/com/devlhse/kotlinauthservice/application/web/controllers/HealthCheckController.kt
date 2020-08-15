package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.domain.services.HealthCheckService
import io.javalin.http.Context
import org.koin.core.KoinComponent
import org.koin.core.inject

object HealthCheckController : KoinComponent {

    private val healthCheckService by inject<HealthCheckService>()

    fun status(ctx: Context) {
        ctx.json(healthCheckService.status())
    }

    fun statusComplete(ctx: Context) {
        ctx.json(healthCheckService.statusComplete())
    }
}
