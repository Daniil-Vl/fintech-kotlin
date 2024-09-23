package ru.tbank

import ru.tbank.dsl.readme

fun main() {
    val res = readme {
        header(level = 1) { +"New title" }
        text {
            +italic(text = "This is italic text")
            +bold(text = "This is bold text")
            +underlined(text = "This is underlined text")
            +strike(text = "This is strike text")
        }
    }
    println(res)
}