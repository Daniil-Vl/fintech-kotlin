package ru.tbank.springapp.dao

interface Repository<K, V> {
    fun getAll(): List<V>

    fun getById(slug: K): V?

    fun save(slug: K, t: V): V?

    fun update(slug: K, t: V): V?

    fun delete(slug: K): V?
}