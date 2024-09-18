package ru.tbank.dto_1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tbank.deserializer.LocalDateSerializer
import java.time.LocalDate
import kotlin.math.exp

@Serializable
data class News(
    val id: Int,

    @SerialName("publication_date")
    @Serializable(with = LocalDateSerializer::class)
    val publicationDate: LocalDate,

    val title: String,
    val place: Place?,
    val description: String,

    @SerialName("site_url") val siteUrl: String,
    @SerialName("favorites_count") val favoritesCount: Int,
    @SerialName("comments_count") val commentsCount: Int,
) {
    @Serializable
    data class Place(
        val id: Int,
        val title: String,
        val address: String,
        @SerialName("site_url") val siteUrl: String,
    )

    val rating: Double by lazy {
        1 / (1 + exp(-(favoritesCount.toDouble() / (commentsCount + 1))))
    }
}