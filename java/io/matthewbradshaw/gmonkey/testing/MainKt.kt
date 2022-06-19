package io.matthewbradshaw.gmonkey.testing

import io.matthewbradshaw.gmonkey.core.otto
import io.matthewbradshaw.gmonkey.core.model.Paradigm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class MainKt {
  fun main() {
    runBlocking {
      val octavius = otto(Paradigm.FLATWARE)
      val game = CubeGame(octavius)
      octavius.engine().play(game)
      Test()
      while (true) delay(1000000000000L)
    }
  }
}