package io.jackbradshaw.otter.demo

import io.jackbradshaw.otter.config.Config
import kotlinx.coroutines.delay
import io.jackbradshaw.otter.engine.config.Config as EngineConfig
import kotlinx.coroutines.runBlocking
import io.jackbradshaw.otter.otter
import io.jackbradshaw.otter.openxr.config.defaultConfig as defaultOpenXrConfig

class MainKt {
  fun main() {
    runBlocking {
      try {
      val platform = otter(CONFIG)
      val game = demo(platform)
        platform.engine()
      platform.stage().addItem(game.world())
      suspendForever()
      } catch (t: Throwable) {
        t.printStackTrace()
      }
    }
  }

  private suspend fun suspendForever() {
    while (true) delay(Long.MAX_VALUE)
  }

  companion object {
    private val CONFIG = Config.newBuilder()
        .setEngineConfig(EngineConfig.newBuilder().setXrEnabled(true).setHeadlessEnabled(false).setDebugEnabled(false).build())
        .setOpenXrConfig(defaultOpenXrConfig)
        .build()
  }
}
