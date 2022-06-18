package io.matthewbradshaw.octavius.test

import io.matthewbradshaw.octavius.otto
import io.matthewbradshaw.octavius.core.Paradigm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class MainKt {
  fun main() {
    runBlocking {
     /* val octavius = otto(Paradigm.VR)
      val game = CubeGame(octavius)
      octavius.engine().core.play(game)
      octavius.ignition().go()*/
      Test()
      while (true) delay(1000000000000L)
    }
  }
}