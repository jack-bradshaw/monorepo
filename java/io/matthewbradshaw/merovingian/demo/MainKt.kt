package java.io.matthewbradshaw.merovingian.demo

import io.matthewbradshaw.merovingian.config.Paradigm
import io.matthewbradshaw.merovingian.demo.demo
import io.matthewbradshaw.merovingian.merovingian
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class MainKt {
  fun main() {
    runBlocking {
      val merovingian = merovingian(Paradigm.VR)
      val demo = demo(merovingian)
      val world = demo.world()
      merovingian.hostFactory().create(world).go()
      while (true) delay(1000000000000L)
    }
  }
}
