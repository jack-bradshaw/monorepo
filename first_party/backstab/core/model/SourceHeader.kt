package com.jackbradshaw.backstab.core.model

/**
 * A source file without associated contents (i.e. package and filename).
 *
 * @property packageName The package name of the source file.
 * @property fileName The name of the osurce file (excluding extension).
 * @property extension the extension of the source file (excluding dot).
 */
data class SourceHeader(
    val packageName: String,
    val fileName: String,
    val extension: String = "kt",
)
