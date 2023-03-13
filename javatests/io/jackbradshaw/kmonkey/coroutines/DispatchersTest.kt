package io.jackbradshaw.kmonkey.coroutines

import kotlinx.coroutines.runBlocking
import org.junit.Test

class DispatchersTest {

  @Test
  fun createRenderingDispatcher_calledMultipleTimes_getsSameInstance() =
      runBlocking {
        // TODO(jack-bradshaw): Figure our how to write tests for JMonkey.
      }

  @Test
  fun createPhysicsDisaptcher_calledMultipleTimes_getsSameInstance() =
      runBlocking {
        // TODO(jack-bradshaw): Figure our how to write tests for JMonkey.
      }

  @Test
  fun launchWithRenderingDispatcher_usesRenderingThread() =
      runBlocking {
        // TODO(jack-bradshaw): Figure our how to write tests for JMonkey.
      }

  @Test
  fun launchWithPhysicsDispatcher_usesPhysicsThread() =
      runBlocking {
        // TODO(jack-bradshaw): Figure our how to write tests for JMonkey.
      }
}
