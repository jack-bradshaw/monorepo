package com.jackbradshaw.kale.model

/** An in-memory compiled Java/Kotlin class file. */
data class JvmClass(
    val packageName: String = "",
    val fileName: String,
    val contents: List<Byte> = emptyList()
)
