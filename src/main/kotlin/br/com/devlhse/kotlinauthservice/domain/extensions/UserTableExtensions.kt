package br.com.devlhse.kotlinauthservice.domain.extensions

import br.com.devlhse.kotlinauthservice.domain.model.entity.User
import br.com.devlhse.kotlinauthservice.domain.model.response.UserResponse
import br.com.devlhse.kotlinauthservice.resources.tables.UserTable
import org.jetbrains.exposed.sql.ResultRow

fun UserTable.toUserResponse(it: ResultRow) : UserResponse =
    UserResponse(
        id = it[id].value,
        name = it[name],
        email = it[email],
        createdAt = it[createdAt],
        updatedAt = it[updatedAt]
    )

fun UserTable.toUser(it: ResultRow): User =
    User(
        name = it[name],
        email = it[email],
        password = it[password],
        id = it[id].value
    )
