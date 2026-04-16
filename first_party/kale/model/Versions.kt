package com.jackbradshaw.kale.model

/** Kotlin/JVM version information. */
data class Versions(
    /** A target JVM bytecode version. */
    val jvmTarget: String = "1.8",

    /** A Kotlin language version. */
    val languageVersion: String = "2.0",

    /** A Kotlin API version. */
    val apiVersion: String = "2.0"
)
