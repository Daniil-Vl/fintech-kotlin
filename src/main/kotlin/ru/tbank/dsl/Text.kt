package ru.tbank.dsl

class Text : Element() {
    private val content = StringBuilder()

    operator fun String.unaryPlus() {
        content.append(this)
    }

    fun bold(text: String) = "**$text**\n"
    fun italic(text: String) = "_${text}_\n"
    fun underlined(text: String) = "<ins>${text}</ins>\n"
    fun strike(text: String) = "<strike>${text}</strike>\n"

    override fun toString(): String = content.toString()
}