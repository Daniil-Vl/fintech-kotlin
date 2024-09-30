package ru.tbank.springapp.client.impl

import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import ru.tbank.springapp.client.KudagoClient
import ru.tbank.springapp.dto.CategoryDTO
import ru.tbank.springapp.dto.CityDTO

@Service
class KudagoClientImpl(
    private val restClient: RestClient
) : KudagoClient {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getCategories(): List<CategoryDTO> {
        logger.info("Try to get all categories from Kudago API")
        return restClient
            .get()
            .uri("/place-categories/")
            .retrieve()
            .body(object : ParameterizedTypeReference<List<CategoryDTO>>() {})
            ?: emptyList()
    }

    override fun getCities(): List<CityDTO> {
        logger.info("Try to get all cities from Kudago API")
        return restClient
            .get()
            .uri("/locations/")
            .retrieve()
            .body(object : ParameterizedTypeReference<List<CityDTO>>() {})
            ?: emptyList()
    }

}
