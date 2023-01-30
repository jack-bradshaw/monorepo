package io.jackbradshaw.otter.demo

import io.jackbradshaw.otter.config.Config
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.jackbradshaw.otter.merovingian

class MainKt {
  fun main() {
    runBlocking {
      val platform = otter(CONFIG)
      val game = demo(platform)
      platform.stage().attach(game.world())
      suspendForever()
    }
  }

  private suspend fun suspendForever() {
    while (true) delay(Long.MAX_VALUE)
  }

  companion object {
    private val CONFIG =
        Config(
            vrEnabled = true,
            headlessEnabled = false,
        )
  }
}
