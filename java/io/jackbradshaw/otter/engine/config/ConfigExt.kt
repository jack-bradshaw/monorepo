package io.jackbradshaw.otter.engine.config

val defaultConfig =
    Config.newBuilder().setXrEnabled(false).setHeadlessEnabled(false).setDebugEnabled(false).build()
