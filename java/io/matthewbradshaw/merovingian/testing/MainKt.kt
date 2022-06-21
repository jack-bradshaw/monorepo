package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.config.Paradigm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class MainKt {
  fun main() {
    runBlocking {
      val merovingian = merovingian(Paradigm.FLATWARE)
      val testing = testing(merovingian)
      /*val game = CubeGame(octavius)
      octavius.engine().play(game)
      Test()
      while (true) delay(1000000000000L)*/
    }
  }
}