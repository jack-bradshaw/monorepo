package io.jackbradshaw.clearxr.config

fun config(actionManifestDirectory: String, actionmanifestFilename: String, actionSetName: String) = Config
    .newBuilder()
    .setActionManifestDirectory(actionManifestDirectory)
    .setActionManifestFilename(actionmanifestFilename)
    .setActionSetName(actionSetName)
    .build()

val defaultConfig = config(System.getProperty("java.io.tmpdir"), "clearxr_action_manifest.json", "omniset")