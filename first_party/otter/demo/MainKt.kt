package com.jackbradshaw.otter.demo

import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.engine.config.Config as EngineConfig
import com.jackbradshaw.otter.openxr.config.defaultConfig as defaultOpenXrConfig
import com.jackbradshaw.otter.otter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() {
    runBlocking {
      val platform = otter(CONFIG)
      val game = demo(platform)
      platform.engine()
      platform.stage().addItem(game.world())
      suspendForever()
    }
  }

  private suspend fun suspendForever() {
    while (true) delay(Long.MAX_VALUE)
  }

  companion object {
    private val CONFIG =
        Config.newBuilder()
            .setEngineConfig(
                EngineConfig.newBuilder()
                    .setXrEnabled(true)
                    .setHeadlessEnabled(false)
                    .setDebugEnabled(false)
                    .build())
            .setOpenXrConfig(defaultOpenXrConfig)
            .build()
  }
}
