package io.jackbradshaw.otter.openxr.config

val defaultConfig =
    Config.newBuilder()
        .setActionManifestDirectory(System.getProperty("java.io.tmpdir"))
        .setActionManifestFilename("otter_action_manifest.json")
        .setActionSetName("main")
        .build()
