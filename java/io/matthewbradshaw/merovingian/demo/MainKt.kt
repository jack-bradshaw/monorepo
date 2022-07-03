package io.matthewbradshaw.merovingian.demo

import io.matthewbradshaw.merovingian.config.Config
import io.matthewbradshaw.merovingian.merovingian
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() {
    runBlocking {
      val merovingian = merovingian(CONFIG)
      val game = demo(merovingian)
      val world = game.world()
      merovingian.host().run(world)
      waitForever()
    }
  }

  private suspend fun waitForever() {
    while (true) delay(1000000000000L)
  }

  companion object {
    private val CONFIG = Config(
      vrEnabled = true,
      debugEnabled = false,
      headlessEnabled = false,
    )
  }
}
