package br.com.devlhse.kotlinauthservice.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.LocalDate
import java.time.LocalDateTime

object ObjectMapperConfig {
    val jsonObjectMapper: ObjectMapper = configJsonMapper()

    private fun configJsonMapper(): ObjectMapper = jacksonObjectMapper().apply {
        this.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        this.registerKotlinModule()
        this.registerModule(dateModule())
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private fun dateModule(): SimpleModule = with(SimpleModule()) {
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
        addSerializer(LocalDate::class.java, LocalDateSerializer())
        addDeserializer(LocalDate::class.java, LocalDateDeserializer())
    }
}
