package ru.tbank.dsl

@DslMarker
annotation class MarkdownDsl

@MarkdownDsl
class Readme {
    private val elements = mutableListOf<Element>()

    fun header(level: Int, init: Header.() -> Unit) {
        elements.addLast(
            Header(level).apply(init)
        )
    }

    fun text(init: Text.() -> Unit) {
        elements.addLast(
            Text().apply(init)
        )
    }

    override fun toString(): String = elements.joinToString(separator = "\n")
}

fun readme(init: Readme.() -> Unit): Readme = Readme().apply(init)