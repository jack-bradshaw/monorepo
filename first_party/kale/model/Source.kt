package com.jackbradshaw.kale.model

/** An in-memory Java/Kotlin source file. */
data class Source(
    /** The name of the file excluding the extension. */
    val fileName: String,

    /** The file extension without the leading dot. */
    val extension: String = "kt",

    /** The fully qualified package name. */
    val packageName: String = "",

    /** The text contents of the file. */
    val contents: String = ""
)
