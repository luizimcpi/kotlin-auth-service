package br.com.devlhse.kotlinauthservice.domain.common.utils

import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import io.javalin.core.security.Role
import java.util.Date

@SuppressWarnings("MagicNumber")
class JwtProvider(private val cipher: Cipher) {

    fun decodeJWT(token: String): DecodedJWT = JWT.require(cipher.algorithm).build().verify(token)

    fun createJWT(user: User, role: Role): String? =
        JWT.create()
            .withIssuedAt(Date())
            .withSubject(user.email)
            .withClaim("role", role.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000))
            .sign(cipher.algorithm)
}
