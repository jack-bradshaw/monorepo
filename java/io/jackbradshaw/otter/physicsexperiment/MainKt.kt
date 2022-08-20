package io.jackbradshaw.otter.physics.experiment

import io.jackbradshaw.otter.config.Config
import java.io.jackbradshaw.otter.merovingian
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() {
    runBlocking {
      val merovingian = merovingian(CONFIG)
      val game = physicsExperiment(merovingian)
      val world = game.world()
      merovingian.host().run(world)
      suspendForever()
    }
  }

  private suspend fun suspendForever() {
    while (true) delay(Long.MAX_VALUE)
  }

  companion object {
    private val CONFIG = Config(
      vrEnabled = true,
      headlessEnabled = false,
    )
  }
}
