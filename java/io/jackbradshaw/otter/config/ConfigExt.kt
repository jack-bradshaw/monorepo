package io.jackbradshaw.otter.config

val defaultConfig =
    Config.newBuilder()
        .setEngineConfig(io.jackbradshaw.otter.engine.config.defaultConfig)
        .setOpenXrConfig(io.jackbradshaw.otter.openxr.config.defaultConfig)
        .build()
