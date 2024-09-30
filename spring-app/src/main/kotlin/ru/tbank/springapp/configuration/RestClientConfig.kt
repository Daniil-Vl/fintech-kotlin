package ru.tbank.springapp.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfig {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun restClient(): RestClient = RestClient.builder()
        .baseUrl("https://kudago.com/public-api/v1.4")
        .defaultStatusHandler(
            { obj: HttpStatusCode -> obj.isError },
            (RestClient.ResponseSpec.ErrorHandler { _: HttpRequest?, _: ClientHttpResponse? ->
                logger.warn("Data fetching from Kudago API failed :(")
            })
        )
        .build()

}