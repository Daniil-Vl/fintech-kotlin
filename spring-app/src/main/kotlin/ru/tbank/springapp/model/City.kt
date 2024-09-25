package ru.tbank.springapp.model

import ru.tbank.springapp.dto.CityDTO

data class City(
    val slug: String,
    val name: String,
) {
    fun toDTO(): CityDTO = CityDTO(slug, name)
}
