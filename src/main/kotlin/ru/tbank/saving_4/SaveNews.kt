package ru.tbank.saving_4

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import org.slf4j.LoggerFactory
import ru.tbank.dto_1.News
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Path

class NewsCsvSaver(private val filepath: Path) {

    @OptIn(ExperimentalSerializationApi::class)
    private val csv = Csv { hasHeaderRecord = true }
    private val writer: PrintWriter = PrintWriter(filepath.toFile())

    private val logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    fun saveNews(news: List<News>) {
        val serialized = csv.encodeToString(ListSerializer(News.serializer()), news)

        try {
            writer.write(serialized)
        } catch (e: IOException) {
            logger.error("Error while saving news in file $filepath", e)
        }
    }

    fun close() {
        writer.close()
    }

}
