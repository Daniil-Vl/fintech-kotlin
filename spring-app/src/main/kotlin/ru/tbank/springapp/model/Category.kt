package ru.tbank.springapp.model

import ru.tbank.springapp.dto.CategoryDTO

data class Category(
    val slug: String,
    val name: String,
) {
    fun toDTO(): CategoryDTO = CategoryDTO(slug, name)
}
