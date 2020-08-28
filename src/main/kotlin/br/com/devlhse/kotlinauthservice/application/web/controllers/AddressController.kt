package br.com.devlhse.kotlinauthservice.application.web.controllers

import br.com.devlhse.kotlinauthservice.domain.gateway.AddressGateway
import io.javalin.http.Context
import org.apache.logging.log4j.LogManager
import org.eclipse.jetty.http.HttpStatus
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressWarnings("TooGenericExceptionCaught")
object AddressController : KoinComponent {

    private val logger = LogManager.getLogger(AddressController::class.java.name)
    private val addressGateway by inject<AddressGateway>()

    fun getAddressByCep(ctx: Context) {
        val cep = ctx.pathParam("cep")
        var status = HttpStatus.OK_200
        try {
            ctx.json(addressGateway.getAddressByCep(cep))
            ctx.status(status)
        } catch (e: Exception) {
            logger.error("Erro ao consultar endereço pelo cep: $cep erro: $e")
            status = HttpStatus.NO_CONTENT_204
        }
        ctx.status(status)
    }

}
