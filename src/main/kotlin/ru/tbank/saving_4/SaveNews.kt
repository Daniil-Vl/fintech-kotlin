package ru.tbank.saving_4

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import ru.tbank.dto_1.News
import java.io.File

class NewsCsvSaver {

    @OptIn(ExperimentalSerializationApi::class)
    private val csv = Csv { hasHeaderRecord = true }

    @OptIn(ExperimentalSerializationApi::class)
    fun saveNews(path: String, news: List<News>) {
        val serialized = csv.encodeToString(ListSerializer(News.serializer()), news)
        File(path).writeText(serialized)
    }

}