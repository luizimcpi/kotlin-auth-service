package br.com.devlhse.kotlinauthservice.resources.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object ContactTable : IntIdTable("contacts")  {
    val name = varchar("name", length = TableSizes.SIZE_100)
    val email = varchar("email", length = TableSizes.SIZE_100)
    val phone = varchar("phone", length = TableSizes.SIZE_20)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
    val userId = integer("user_id")
}
