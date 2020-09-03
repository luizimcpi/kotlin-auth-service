package br.com.devlhse.kotlinauthservice.utils

import br.com.devlhse.kotlinauthservice.config.DatabaseConfig
import br.com.devlhse.kotlinauthservice.config.EnvironmentConfig
import br.com.devlhse.kotlinauthservice.resources.tables.UserTable
import com.opentable.db.postgres.embedded.EmbeddedPostgres
import io.mockk.every
import io.mockk.mockk
import java.io.File
import java.net.URI
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object PostgresMock {

    private const val EMBEDDED_POSTGRES_PORT = 15432

    private val environment = mockk<EnvironmentConfig>().also {
        every { it.databaseUrl } returns "jdbc:postgresql://localhost:15432/postgres?user=postgres"
        every { it.databaseUser } returns "postgres"
        every { it.databasePassword } returns "postgres"
    }

    private lateinit var embeddedPostgres: EmbeddedPostgres

    fun init() {
        embeddedPostgres = EmbeddedPostgres.builder()
            .setPort(EMBEDDED_POSTGRES_PORT)
            .start()

        runScripts()
    }

    fun close() {
        embeddedPostgres.close()
    }

    fun runScripts() {
        DatabaseConfig(environment).connect()
        val flyway = Flyway.configure()
            .locations("classpath:db")
            .dataSource(
                environment.databaseUrl,
                environment.databaseUser,
                environment.databasePassword
            ).load()

        flyway.clean()
        flyway.migrate()
    }

    fun resetDb() {
        transaction {
            UserTable.deleteAll()
        }
    }

    fun executeScripts(file: String) {
        val script = "/db/$file"

        try {
            transaction {
                exec(readDatabaseData(script))
            }
        } catch (ex: Exception) {
            println("message: ${ex.message}")
        }
    }

    private fun readDatabaseData(path: String): String = File(fileUri(path)).readText(Charsets.UTF_8)

    private fun fileUri(path: String): URI = this::class.java.getResource(path).toURI()
}