package io.jackbradshaw.omnixr.config

fun config(actionManifestDirectory: String) = Config
    .newBuilder()
    .setActionManifestDirectory(actionManifestDirectory)
    .build()

val defaultConfig = config(System.getProperty("java.io.tmpdir"))