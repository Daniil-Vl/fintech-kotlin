package ru.tbank.coroutines

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import ru.tbank.dto_1.News
import ru.tbank.saving_4.NewsCsvSaver
import java.nio.file.Path

class NewsConsumer(filepath: Path) {

    private val csvSaver: NewsCsvSaver = NewsCsvSaver(filepath)

    suspend fun saveNews(channel: ReceiveChannel<List<News>>) {
        coroutineScope {
            channel.consumeEach { news -> csvSaver.saveNews(news) }
        }
        csvSaver.close()
    }
}