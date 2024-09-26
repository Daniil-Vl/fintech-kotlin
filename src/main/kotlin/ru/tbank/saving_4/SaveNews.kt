package ru.tbank.saving_4

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import org.slf4j.LoggerFactory
import ru.tbank.dto_1.News
import java.io.File
import java.io.IOException

class NewsCsvSaver {

    @OptIn(ExperimentalSerializationApi::class)
    private val csv = Csv { hasHeaderRecord = true }

    private val logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    fun saveNews(path: String, news: List<News>) {
        val serialized = csv.encodeToString(ListSerializer(News.serializer()), news)

        try {
            File(path).writeText(serialized)
        } catch (e: IOException) {
            logger.error("Error while saving news in file $path", e)
        }
    }

}
