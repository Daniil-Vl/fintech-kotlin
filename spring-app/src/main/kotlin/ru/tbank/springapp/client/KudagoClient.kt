package ru.tbank.springapp.client

import ru.tbank.springapp.dto.CategoryDTO
import ru.tbank.springapp.dto.CityDTO

interface KudagoClient {

    fun getCategories(): List<CategoryDTO>

    fun getCities(): List<CityDTO>

}