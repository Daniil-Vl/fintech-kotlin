package ru.tbank.springapp.dao.impl

import ru.tbank.springapp.dao.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class RepositoryImpl<K, V>(
    private val map: ConcurrentMap<K, V> = ConcurrentHashMap(),
) : Repository<K, V> {

    override fun getAll(): List<V> = map.values.toList()

    override fun getById(slug: K): V? = map[slug]

    override fun save(slug: K, t: V): V? = map.put(slug, t)

    override fun update(slug: K, t: V): V? = map[slug]?.apply { save(slug, t) }

    override fun delete(slug: K): V? = map.remove(slug)
}