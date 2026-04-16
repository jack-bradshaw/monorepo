package com.jackbradshaw.kale.model

/** Kotlin/JVM versions used for KSP processing. */
data class KspVersions(
    /** The target JVM bytecode version to process against. */
    val jvmTarget: String = "1.8",
    /** The Kotlin language version to process against. */
    val languageVersion: String = "2.0",
    /** The Kotlin API version to process against. */
    val apiVersion: String = "2.0"
)
