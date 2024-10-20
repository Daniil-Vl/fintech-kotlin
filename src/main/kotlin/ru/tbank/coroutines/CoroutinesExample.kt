package ru.tbank.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import ru.tbank.client_2.KudaGoClient
import ru.tbank.dto_1.News
import ru.tbank.saving_4.NewsCsvSaver
import java.nio.file.Path


fun main() {
    val pagesNumber = 4

    blockingExample(pagesNumber)
    coroutineExample(pagesNumber, 2)
    coroutineExample(pagesNumber, 4)
    coroutineExample(pagesNumber, 8)
}

fun blockingExample(pagesNumber: Int) {
    val client = KudaGoClient()
    val saver = NewsCsvSaver(Path.of("dump.csv"))

    val startTime = System.nanoTime()

    for (i in 1..pagesNumber) {
        val res = runBlocking { client.getNews(100, i + 1) }
        saver.saveNews(res)
    }

    val endTime = System.nanoTime()

    println("Time for gathering $pagesNumber pages in blocking way is ${endTime - startTime}ns")
}

fun coroutineExample(pagesNumber: Int, threadsNumber: Int) {
    val threadPoolContext = newFixedThreadPoolContext(threadsNumber, "coroutine-pool")

    val channel = Channel<List<News>>()
    val newsProducer = NewsProducer()
    val newsConsumer = NewsConsumer(Path.of("dump.csv"))

    val startTime = System.nanoTime()

    runBlocking {
        launch {
            newsConsumer.saveNews(channel)
        }

        coroutineScope {
            repeat(pagesNumber) {
                launch(threadPoolContext) {
                    newsProducer.getNews(100, it + 1, channel)
                }
            }
        }

        channel.close()
    }

    val endTime = System.nanoTime()

    println("Time for gathering $pagesNumber pages with $threadsNumber threads with coroutines is ${endTime - startTime}ns")
}

