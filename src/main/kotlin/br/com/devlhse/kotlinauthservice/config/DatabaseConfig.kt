package br.com.devlhse.kotlinauthservice.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database


class DatabaseConfig(private val environment: EnvironmentConfig) {
    fun connect() {
        val config = HikariConfig().apply {
            jdbcUrl = environment.databaseUrl
            username = environment.databaseUser
            password = environment.databasePassword
        }
        Database.connect(HikariDataSource(config))
    }
}
