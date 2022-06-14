package io.matthewbradshaw.octavius.test

import io.matthewbradshaw.octavius.otto
import io.matthewbradshaw.octavius.core.Paradigm

class MainKt {
  fun main() {
    val octavius = otto(Paradigm.VR)
    val game = CubeGame(octavius)
  }
}