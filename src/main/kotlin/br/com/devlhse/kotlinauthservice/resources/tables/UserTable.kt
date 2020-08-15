package br.com.devlhse.kotlinauthservice.resources.tables

import br.com.devlhse.kotlinauthservice.resources.tables.TableSizes.SIZE_100
import br.com.devlhse.kotlinauthservice.resources.tables.TableSizes.SIZE_150
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object UserTable : IntIdTable("tb_user") {
    val name = varchar("name", length = SIZE_100)
    val email = varchar("email", length = SIZE_100)
    val password = varchar("password", length = SIZE_150)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
