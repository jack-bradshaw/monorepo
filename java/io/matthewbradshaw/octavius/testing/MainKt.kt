package io.matthewbradshaw.octavius.testing

import io.matthewbradshaw.octavius.otto
import io.matthewbradshaw.octavius.engine.Paradigm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class MainKt {
  fun main() {
    runBlocking {
      val octavius = otto(Paradigm.FLATWARE)
      val game = CubeGame(octavius)
      octavius.engine().play(game)
      //Test()
      while (true) delay(1000000000000L)
    }
  }
}