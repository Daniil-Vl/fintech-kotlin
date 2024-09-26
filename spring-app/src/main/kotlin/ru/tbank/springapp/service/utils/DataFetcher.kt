package ru.tbank.springapp.service.utils

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.tbank.springapp.aspect.Timed
import ru.tbank.springapp.client.KudagoClient
import ru.tbank.springapp.dao.Repository
import ru.tbank.springapp.dto.CategoryDTO
import ru.tbank.springapp.model.Category
import ru.tbank.springapp.model.City

@Service
private class DataFetcher(
    private val client: KudagoClient,
    private val categoryRepository: Repository<Category>,
    private val cityRepository: Repository<City>
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Timed
    @EventListener(ApplicationReadyEvent::class)
    fun fetchData() {
        logger.info("Fetching data from Kudago API...")

        val categories: List<CategoryDTO> = client.getCategories()
        logger.info("Fetched categories: {}", categories)

        val cities = client.getCities()
        logger.info("Fetched cities: {}", cities)

        logger.info("Saving fetched data...")

        categories.forEach { categoryDTO ->
            categoryRepository.save(categoryDTO.slug, categoryDTO.toCategory())
        }

        cities.forEach { cityDTO ->
            cityRepository.save(cityDTO.slug, cityDTO.toCity())
        }

        logger.info("Fetched data successfully saved")
    }

}