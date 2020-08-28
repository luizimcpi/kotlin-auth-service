package br.com.devlhse.kotlinauthservice.config

import com.natpryce.konfig.Configuration
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.booleanType
import com.natpryce.konfig.getValue
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType

class EnvironmentConfig (configuration: Configuration = EnvironmentVariables()){
    val serverPort = configuration.getOrElse(
        SERVER_PORT,
        DEFAULT_SERVER_PORT
    )
    val enableDebug = configuration.getOrElse(ENABLE_DEBUG, DEFAULT_ENABLE_DEBUG_VALUE)
    val databaseUrl = configuration[DATABASE_URL]
    val databaseUser = configuration[DATABASE_USER]
    val databasePassword = configuration[DATABASE_PASSWORD]
    val authSecret = configuration[AUTH_SECRET]
    val headerTokenName = configuration.getOrElse(HEADER_TOKEN_NAME, DEFAULT_HEADER_TOKEN_NAME)
    val viaCepUrl = configuration[VIACEP_URL]

    companion object {
        private val SERVER_PORT by intType
        private val ENABLE_DEBUG by booleanType
        private val DATABASE_URL by stringType
        private val DATABASE_USER by stringType
        private val DATABASE_PASSWORD by stringType
        private val AUTH_SECRET by stringType
        private val HEADER_TOKEN_NAME by stringType
        private val VIACEP_URL by stringType
        private const val DEFAULT_SERVER_PORT = 7000
        private const val DEFAULT_ENABLE_DEBUG_VALUE = false
        private const val DEFAULT_HEADER_TOKEN_NAME = "Authorization"
    }
}
