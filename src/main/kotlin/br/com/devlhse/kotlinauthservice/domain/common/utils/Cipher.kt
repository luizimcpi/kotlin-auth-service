package br.com.devlhse.kotlinauthservice.domain.common.utils

import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import com.auth0.jwt.algorithms.Algorithm

class Cipher(environmentConfig: EnvironmentConfig) {
    val algorithm: Algorithm = Algorithm.HMAC256(environmentConfig.authSecret)

    fun encrypt(data: String?): ByteArray =
        algorithm.sign(data?.toByteArray(), null)

}
