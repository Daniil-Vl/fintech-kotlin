package ru.tbank.springapp.dao.impl

import ru.tbank.springapp.dao.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class RepositoryImpl<T>(
    private val map: ConcurrentMap<String, T> = ConcurrentHashMap(),
) : Repository<T> {

    override fun getAll(): List<T> = map.values.toList()

    override fun getById(slug: String): T? = map[slug]

    override fun save(slug: String, t: T): T? = map.put(slug, t)

    override fun update(slug: String, t: T): T? = map[slug]?.apply { save(slug, t) }

    override fun delete(slug: String): T? = map.remove(slug)
}