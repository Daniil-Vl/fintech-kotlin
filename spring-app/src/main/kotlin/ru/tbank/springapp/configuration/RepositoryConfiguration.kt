package ru.tbank.springapp.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tbank.springapp.dao.Repository
import ru.tbank.springapp.dao.impl.RepositoryImpl
import ru.tbank.springapp.model.Category
import ru.tbank.springapp.model.City

@Configuration
class RepositoryConfiguration {

    @Bean
    fun categoryRepository(): Repository<Category> = RepositoryImpl()

    @Bean
    fun cityRepository(): Repository<City> = RepositoryImpl()

}