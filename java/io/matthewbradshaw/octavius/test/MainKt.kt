package io.matthewbradshaw.octavius.test

import io.matthewbradshaw.octavius.otto
import io.matthewbradshaw.octavius.core.Paradigm
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() {
    runBlocking {
      val octavius = otto(Paradigm.VR)
      val game = CubeGame(octavius)
      octavius.driver().play(game)
      octavius.ignition().ignite()
    }
  }
}