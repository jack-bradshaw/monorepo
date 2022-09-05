package io.jackbradshaw.openxr.config

fun config(actionManifestDirectory: String) = Config
    .newBuilder()
    .setActionManifestDirectory(actionManifestDirectory)
    .build()

val defaultConfig = config(System.getProperty("java.io.tempdir"))