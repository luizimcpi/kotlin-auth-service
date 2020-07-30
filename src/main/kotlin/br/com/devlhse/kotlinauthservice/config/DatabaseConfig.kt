package br.com.devlhse.kotlinauthservice.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database


object DatabaseConfig {
    fun connect() {
        val config = HikariConfig()
        config.jdbcUrl = EnvironmentConfig().databaseUrl
        config.username = EnvironmentConfig().databaseUser
        config.password = EnvironmentConfig().databasePassword

        Database.connect(HikariDataSource(config))
    }
}
