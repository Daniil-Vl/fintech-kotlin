package ru.tbank.saving_4

import ru.tbank.dto_1.News
import java.io.FileWriter
import java.io.PrintWriter

fun saveNews(path: String, news: Collection<News>) {
    val writer = PrintWriter(FileWriter(path), false)

    writer.println(""""id", "publication_date", "title", "place", "description", "siteUrl", "favorites_count", "comments_count"""")

    news.forEach {
        writer.println("${it.id}, ${it.publicationDate}, ${it.title}, ${it.place?.title}, ${it.description}, ${it.siteUrl}, ${it.favoritesCount}, ${it.commentsCount}")
    }

    writer.flush()
}