package io.jackbradshaw.otter.config

import io.jackbradshaw.otter.engine.config.defaultConfig as defaultEngineConfig
import io.jackbradshaw.otter.openxr.config.defaultConfig as defaultOpenXrConfig

val defaultConfig = config {
  engineConfig = defaultEngineConfig
  openXrConfig = defaultOpenXrConfig
}