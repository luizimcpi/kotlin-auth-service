package br.com.devlhse.kotlinauthservice.config


import br.com.devlhse.kotlinauthservice.domain.common.utils.JwtProvider
import com.auth0.jwt.interfaces.DecodedJWT
import io.javalin.Javalin
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.ForbiddenResponse

internal enum class Roles : Role {
    ANYONE, AUTHENTICATED
}

class AuthConfig(private val jwtProvider: JwtProvider, private val environmentConfig: EnvironmentConfig) {
    fun configure(app: Javalin) {
        app.config.accessManager { handler, ctx, permittedRoles ->
            val jwtToken = getJwtTokenHeader(ctx)
            val userRole = getUserRole(jwtToken) ?: Roles.ANYONE
            permittedRoles.takeIf { !it.contains(userRole) }?.apply { throw ForbiddenResponse() }
            ctx.attribute("email", getEmail(jwtToken))
            handler.handle(ctx)
        }
    }

    fun getJwtTokenHeader(ctx: Context): DecodedJWT? {
        val tokenHeader = ctx.header(environmentConfig.headerTokenName)?.substringAfter("Bearer")?.trim()
            ?: return null

        return jwtProvider.decodeJWT(tokenHeader)
    }

    fun getEmail(jwtToken: DecodedJWT?): String? {
        return jwtToken?.subject
    }

    private fun getUserRole(jwtToken: DecodedJWT?): Role? {
        val userRole = jwtToken?.getClaim("role")?.asString() ?: return null
        return Roles.valueOf(userRole)
    }
}
