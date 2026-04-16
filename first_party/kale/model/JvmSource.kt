package com.jackbradshaw.kale.model

/** An in-memory Java/Kotlin source file. */
data class JvmSource(
    val packageName: String = "",
    val fileName: String,
    val extension: String = "kt",
    val contents: String = ""
)
