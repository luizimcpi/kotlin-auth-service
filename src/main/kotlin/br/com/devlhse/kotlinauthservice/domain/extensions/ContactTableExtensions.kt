package br.com.devlhse.kotlinauthservice.domain.extensions

import br.com.devlhse.kotlinauthservice.domain.model.response.ContactResponse
import br.com.devlhse.kotlinauthservice.resources.tables.ContactTable
import org.jetbrains.exposed.sql.ResultRow

fun ContactTable.toContactResponse(it: ResultRow) : ContactResponse =
    ContactResponse(
        id = it[id].value,
        name = it[name],
        email = it[email],
        phone = it[phone],
        createdAt = it[createdAt],
        updatedAt = it[updatedAt]
    )
