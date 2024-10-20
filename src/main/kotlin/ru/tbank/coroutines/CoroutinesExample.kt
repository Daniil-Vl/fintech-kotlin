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
import java.time.Duration
import java.time.Instant


fun main() {
    val pagesNumber = 4

    val blockingDuration = blockingExample(pagesNumber)
    val twoThreadsDuration = coroutineExample(pagesNumber, 2)
    val fourThreadsDuration = coroutineExample(pagesNumber, 4)
    val eightThreadsDuration = coroutineExample(pagesNumber, 8)

    println("Requesting $pagesNumber pages of 100 news takes $blockingDuration in blocking way")
    println("Requesting $pagesNumber pages of 100 news takes $twoThreadsDuration with coroutines with 2 threads")
    println("Requesting $pagesNumber pages of 100 news takes $fourThreadsDuration with coroutines with 4 threads")
    println("Requesting $pagesNumber pages of 100 news takes $eightThreadsDuration with coroutines with 8 threads")
}

fun blockingExample(pagesNumber: Int): Duration {
    val client = KudaGoClient()
    val saver = NewsCsvSaver(Path.of("dump.csv"))

    val startTime = System.nanoTime() / 1_000_000

    for (i in 1..pagesNumber) {
        val res = runBlocking { client.getNews(100, i + 1) }
        saver.saveNews(res)
    }

    val endTime = System.nanoTime() / 1_000_000

    return Duration.between(Instant.ofEpochMilli(startTime), Instant.ofEpochMilli(endTime))
}

fun coroutineExample(pagesNumber: Int, threadsNumber: Int): Duration {
    val threadPoolContext = newFixedThreadPoolContext(threadsNumber, "coroutine-pool")

    val channel = Channel<List<News>>()
    val newsProducer = NewsProducer()
    val newsConsumer = NewsConsumer(Path.of("dump.csv"))

    val startTime = System.nanoTime() / 1_000_000

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

    val endTime = System.nanoTime() / 1_000_000

    return Duration.between(Instant.ofEpochMilli(startTime), Instant.ofEpochMilli(endTime))
}

