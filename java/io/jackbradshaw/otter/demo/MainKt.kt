package io.jackbradshaw.otter.demo

import io.jackbradshaw.otter.config.Config
import io.jackbradshaw.otter.engine.config.Config as EngineConfig
import io.jackbradshaw.otter.openxr.config.defaultConfig as defaultOpenXrConfig
import io.jackbradshaw.otter.otter
import kotlinx.coroutines.delay
import io.jackbradshaw.otter.engine.config.Config as EngineConfig
import kotlinx.coroutines.runBlocking
<<<<<<< HEAD
import io.jackbradshaw.otter.otter
import io.jackbradshaw.otter.openxr.config.defaultConfig as defaultOpenXrConfig
=======
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

class MainKt {
  fun main() {
    runBlocking {
      try {
<<<<<<< HEAD
      val platform = otter(CONFIG)
      val game = demo(platform)
        platform.engine()
      platform.stage().addItem(game.world())
      suspendForever()
=======
        val platform = otter(CONFIG)
        val game = demo(platform)
        platform.engine()
        platform.stage().addItem(game.world())
        suspendForever()
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
      } catch (t: Throwable) {
        t.printStackTrace()
      }
    }
  }

  private suspend fun suspendForever() {
    while (true) delay(Long.MAX_VALUE)
  }

  companion object {
<<<<<<< HEAD
    private val CONFIG = Config.newBuilder()
        .setEngineConfig(EngineConfig.newBuilder().setXrEnabled(true).setHeadlessEnabled(false).setDebugEnabled(false).build())
        .setOpenXrConfig(defaultOpenXrConfig)
        .build()
=======
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
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
  }
}
