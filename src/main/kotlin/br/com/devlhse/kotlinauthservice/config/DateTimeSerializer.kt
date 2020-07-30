package br.com.devlhse.kotlinauthservice.config

import br.com.devlhse.kotlinauthservice.domain.extensions.toIsoLocalDate
import br.com.devlhse.kotlinauthservice.domain.extensions.toIsoLocalDateTime
import br.com.devlhse.kotlinauthservice.domain.extensions.toLocalDate
import br.com.devlhse.kotlinauthservice.domain.extensions.toLocalDateTime
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateDeserializer: StdDeserializer<LocalDate>(LocalDate::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDate =
        p.readValueAs(String::class.java).toLocalDate()
}

class LocalDateTimeDeserializer: StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDateTime =
        p.readValueAs(String::class.java).toLocalDateTime()
}

class LocalDateSerializer: StdSerializer<LocalDate>(LocalDate::class.java) {

    override fun serialize(value: LocalDate, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value.toIsoLocalDate())
    }
}

class LocalDateTimeSerializer: StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun serialize(value: LocalDateTime, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value.toIsoLocalDateTime())
    }
}
