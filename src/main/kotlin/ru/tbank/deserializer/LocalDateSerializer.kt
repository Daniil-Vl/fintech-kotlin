package ru.tbank.deserializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer {
    override fun serialize(encoder: Encoder, value: LocalDate) {
        val str: Long = value.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli() / 1000
        encoder.encodeString(str.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate =
        Instant
            .ofEpochSecond(decoder.decodeLong())
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
}