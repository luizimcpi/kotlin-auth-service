package br.com.devlhse.kotlinauthservice.domain.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.toIsoLocalDate(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE)

fun LocalDateTime.toIsoLocalDateTime(): String = this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
