package ru.tbank.springapp.dto

import ru.tbank.springapp.model.Category

data class CategoryDTO(
    val slug: String,
    val name: String,
) {
    fun toCategory(): Category = Category(slug, name)
}
