package ru.tbank.springapp.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.tbank.springapp.dao.Repository
import ru.tbank.springapp.model.City
import ru.tbank.springapp.service.CityService

@Service
class CityServiceImpl(
    private val cityRepository: Repository<City>
) : CityService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAll(): List<City> {
        logger.info("Trying to get all cities")
        return cityRepository.getAll()
    }

    override fun findById(slug: String): City? {
        logger.info("Trying to find city by id $slug")
        return cityRepository.getById(slug)
    }

    override fun create(slug: String, name: String): City? {
        val city = City(slug, name)
        logger.info("Trying to create city $city")
        return cityRepository.save(slug, city)
    }

    override fun update(slug: String, name: String): City? {
        logger.info("Trying to update city with id $slug")
        val city = City(slug, name)
        return cityRepository.update(slug, city)
    }

    override fun delete(slug: String): City? {
        logger.info("Trying to remove city with id $slug")
        return cityRepository.delete(slug)
    }
}