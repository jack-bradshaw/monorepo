package com.jackbradshaw.kale.model

/** An in-memory binary resource file. */
data class Resource(
    /** The relative directory path of this resource. */
    val directoryPath: String = "",

    /** The name of the file excluding the extension. */
    val fileName: String,

    /** The file extension without the leading dot. */
    val extension: String,

    /** The raw byte contents of the file. */
    val contents: List<Byte> = emptyList()
)
