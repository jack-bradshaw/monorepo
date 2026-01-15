package com.jackbradshaw.otter.demo

import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.engine.config.Config as EngineConfig
import com.jackbradshaw.otter.openxr.config.defaultConfig as defaultOpenXrConfig
import com.jackbradshaw.otter.otterComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() {
    runBlocking {
      val platform = otterComponent(CONFIG)
      val game = demoComponent(platform)
      platform.engineCore()
      platform.sceneStage().addItem(game.cubeLevel())
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
