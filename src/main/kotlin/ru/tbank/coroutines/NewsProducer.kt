package ru.tbank.coroutines

import kotlinx.coroutines.channels.SendChannel
import ru.tbank.client_2.KudaGoClient
import ru.tbank.dto_1.News

class NewsProducer {

    private val client = KudaGoClient()

    suspend fun getNews(count: Int, page: Int, channel: SendChannel<List<News>>) {
        val news = client.getNews(count = count, page = page)
        channel.send(news)
    }
}