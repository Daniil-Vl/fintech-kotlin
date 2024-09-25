package ru.tbank.springapp.service

import ru.tbank.springapp.model.Category

interface CategoryService {
    fun findAll(): List<Category>

    fun findById(slug: String): Category?

    fun create(slug: String, name: String): Category?

    fun update(slug: String, name: String): Category?

    fun delete(slug: String): Category?
}