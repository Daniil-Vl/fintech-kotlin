package ru.tbank.springapp.dao

interface Repository<T> {
    fun getAll(): List<T>

    fun getById(slug: String): T?

    fun save(slug: String, t: T): T?

    fun update(slug: String, t: T): T?

    fun delete(slug: String): T?
}