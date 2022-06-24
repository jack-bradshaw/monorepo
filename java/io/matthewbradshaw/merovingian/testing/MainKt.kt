package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.config.Paradigm
import io.matthewbradshaw.merovingian.merovingian
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class MainKt {
  fun main() {
    runBlocking {
      val merovingian = merovingian(Paradigm.VR)
      val testing = testing(merovingian)
      val game = testing.game().create()
      merovingian.hostFactory().create(game).go()
      while (true) delay(1000000000000L)
    }
  }
}
