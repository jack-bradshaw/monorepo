package io.matthewbradshaw.merovingian.physicsexperiment

import io.matthewbradshaw.merovingian.config.Config
import io.matthewbradshaw.merovingian.merovingian
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
