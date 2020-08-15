package br.com.devlhse.kotlinauthservice.config

import br.com.devlhse.kotlinauthservice.exception.ConflictException
import br.com.devlhse.kotlinauthservice.exception.NotFoundException
import br.com.devlhse.kotlinauthservice.exception.UnauthorizedException
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import io.javalin.Javalin
import org.eclipse.jetty.http.HttpStatus

object ErrorExceptionMapping {

    internal data class ErrorResponse(val error: Map<String,String>)

    fun register(app: Javalin) {
        app.exception(JWTDecodeException::class.java) { _, ctx ->
            val error = ErrorResponse(mapOf("message" to "Invalid accessToken !"))
            ctx.json(error).status(HttpStatus.UNAUTHORIZED_401)
        }
        app.exception(UnauthorizedException::class.java) { _, ctx ->
            val error = ErrorResponse(mapOf("message" to "Invalid accessToken !"))
            ctx.json(error).status(HttpStatus.UNAUTHORIZED_401)
        }
        app.exception(SignatureVerificationException::class.java) { _, ctx ->
            val error = ErrorResponse(mapOf("message" to "Invalid accessToken !"))
            ctx.json(error).status(HttpStatus.UNAUTHORIZED_401)
        }
        app.exception(TokenExpiredException::class.java) { _, ctx ->
            val error = ErrorResponse(mapOf("message" to "Invalid accessToken !"))
            ctx.json(error).status(HttpStatus.UNAUTHORIZED_401)
        }
        app.exception(NotFoundException::class.java) { e, ctx ->
            val error = ErrorResponse(mapOf("message" to e.message!!))
            ctx.json(error).status(HttpStatus.NOT_FOUND_404)
        }
        app.exception(ConflictException::class.java) { e, ctx ->
            val error = ErrorResponse(mapOf("message" to e.message!!))
            ctx.json(error).status(HttpStatus.CONFLICT_409)
        }
    }
}
