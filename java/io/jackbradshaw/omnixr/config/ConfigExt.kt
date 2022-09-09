package io.jackbradshaw.omnixr.config

fun config(actionManifestDirectory: String, actionmanifestFilename: String) = Config
    .newBuilder()
    .setActionManifestDirectory(actionManifestDirectory)
    .setActionManifestFilename(actionmanifestFilename)
    .build()

val defaultConfig = config(System.getProperty("java.io.tmpdir"), "omnixr_action_manifest.json")