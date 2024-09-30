package ru.tbank.filtering_2

import ru.tbank.client_2.KudaGoClient
import ru.tbank.dto_1.News
import java.time.LocalDate


class NewsFilter {

    private val client = KudaGoClient()

    fun getMostRatedNews(count: Int, period: ClosedRange<LocalDate>): List<News> {
        var pageIndex: Int = 1
        val news: MutableList<News> = ArrayList()
        var currentBatch: List<News> = client.getNews(count = 100, page = pageIndex++)
        news.addAll(currentBatch)

        var maxElem: News = currentBatch.last()

        // Получаем все элементы, дата которых не больше конца заданного интервала
        while (maxElem.publicationDate < period.endInclusive) {
            currentBatch = client.getNews(count = 100, page = pageIndex++)
            news.addAll(currentBatch)
            maxElem = currentBatch.last()
        }

        return news
            .filter { it.publicationDate >= period.start && it.publicationDate <= period.endInclusive }
            .sortedByDescending { it.rating }
            .take(count)
            .toMutableList()
    }
}


