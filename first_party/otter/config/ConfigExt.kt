package com.jackbradshaw.otter.config

val defaultConfig =
    Config.newBuilder()
        .setEngineConfig(com.jackbradshaw.otter.engine.config.defaultConfig)
        .setOpenXrConfig(com.jackbradshaw.otter.openxr.config.defaultConfig)
        .build()
