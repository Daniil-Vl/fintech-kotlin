package ru.tbank.springapp.service

import ru.tbank.springapp.model.City

interface CityService {
    fun findAll(): List<City>

    fun findById(slug: String): City?

    fun create(slug: String, name: String): City?

    fun update(slug: String, name: String): City?

    fun delete(slug: String): City?
}