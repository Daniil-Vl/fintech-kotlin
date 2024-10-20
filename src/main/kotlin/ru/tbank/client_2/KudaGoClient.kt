package ru.tbank.client_2

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.tbank.dto_1.News

class KudaGoClient {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val baseUrl: String = "https://kudago.com/public-api/v1.4/news/"
    private val httpClient: HttpClient = HttpClient()
    private val json: Json = Json { ignoreUnknownKeys = true }

    private suspend fun get(count: Int, page: Int): HttpResponse {
        return httpClient.get(baseUrl) {
            parameter("page", page.toString())
            parameter("page_size", count)
            parameter("order_by", "publication_date")
            parameter("expand", "place")
            parameter("location", "spb")
            parameter(
                "fields",
                "id,title,place,description,site_url,favorites_count,comments_count,publication_date"
            )
        }
    }

    suspend fun getNews(count: Int = 10, page: Int = 1): List<News> {
        if (count > 100)
            throw IllegalArgumentException("Cannot load more than 100 news")

        var result: List<News> = emptyList()

        try {
            val response: HttpResponse = get(count, page);

            logger.info("Get info from KudaGo API")
            logger.info("Trying to parse json from KudaGo API...")

            result = json
                .parseToJsonElement(response.bodyAsText()).jsonObject["results"]
                ?.jsonArray
                ?.map { it.jsonObject }
                ?.map { json.decodeFromString(it.toString()) }
                ?: emptyList()

            logger.info("Json successfully parsed")
        } catch (exc: Exception) {
            logger.warn("Failed to gather news from Kudago")
            logger.warn(exc.message)
        }

        return result
    }
}