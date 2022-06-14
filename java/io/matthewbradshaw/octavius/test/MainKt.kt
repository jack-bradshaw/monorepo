package java.io.matthewbradshaw.octavius.test

import io.matthewbradshaw.octavius.otto

class MainKt {
  fun main() {
    val octavius = otto(Paradigm.VR)
    val game = CubeGame(octavius)
  }
}