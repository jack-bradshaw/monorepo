package io.matthewbradshaw.merovingian.demo

import io.matthewbradshaw.merovingian.config.Paradigm
import io.matthewbradshaw.merovingian.merovingian
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() {
    runBlocking {
      val merovingian = merovingian(Paradigm.VR)
      val game = demo(merovingian)
      val world = game.world()
      merovingian.host().run(world)
      while (true) delay(1000000000000L)
    }
  }
}
