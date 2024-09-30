package ru.tbank.springapp.dto

import ru.tbank.springapp.model.City

data class CityDTO(
    val slug: String,
    val name: String,
) {
    fun toCity(): City = City(slug, name)
}
