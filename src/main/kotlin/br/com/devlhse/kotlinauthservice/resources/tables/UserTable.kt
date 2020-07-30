package br.com.devlhse.kotlinauthservice.resources.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

@SuppressWarnings("MagicNumber")
object UserTable : IntIdTable("tb_user") {
    val name = varchar("name", length = 100)
    val email = varchar("email", length = 100)
    val password = varchar("password", length = 150)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
