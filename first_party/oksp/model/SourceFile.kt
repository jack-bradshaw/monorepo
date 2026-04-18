package com.jackbradshaw.oksp.model

/**
 * A JVM source file.
 *
 * @property packageName The package name of the source file.
 * @property fileName The name of the source file (excluding extension).
 * @property extension the extension of the source file (excluding dot).
 * @property contents The raw string contents of the file.
 */
data class SourceFile(
    val fileName: String,
    val extension: String = "kt",
    val packageName: String = "",
    val contents: String = "",
)
