package io.jackbradshaw.queen.sustainment.omnisustainer

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.OmniSustainers
import io.jackbradshaw.queen.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.sustainment.startstop.StartStopSimplex
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OmniSustainerSimplexTest {

  private val omniSustainer =
      OmniSustainers.create<Operation<StartStop>>(StartStopOperation.WORK_TYPE)

  private val operation1 = StartStopSimplex()

  private val sustainment1 =
      object : Sustainable<Operation<StartStop>> {
        override val operation =
            object : StartStopOperation() {
              override fun work() = operation1
            }
      }

  private var operation2Started = false
  private val operation2 =
      GlobalScope.launch {
        operation2Started = true
        suspendCancellableCoroutine { /* keep alive indefinitely */}
      }

  private val sustainment2 =
      object : Sustainable<Operation<Job>> {
        override val operation =
            object : KtCoroutineOperation() {
              override fun work() = operation2
            }
      }

  @Test
  fun whenNotSustained_callToControllerSustain_haveNEffect() {
    omniSustainer.sustain(sustainment1)
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenNotSustained_callToControllerRelease_haveNEffect() {
    omniSustainer.sustain(sustainment1)
    omniSustainer.release(sustainment1)
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenNotSustained_callToControllerReleaseAll_haveNoEffect() {
    omniSustainer.sustain(sustainment1)
    omniSustainer.releaseAll()
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenBecomesSustained_prevlousCallToControllerSustain_processedAsynchronously() = runBlocking {
    omniSustainer.sustain(sustainment1)
    omniSustainer.operation.work().start()
    delay(DELAY_MS)
    assertThat(operation1.isStarted()).isTrue()
  }

  @Test
  fun whenBecomesSustained_newCallsToControllerSustain_processedAsynchronously() = runBlocking {
    omniSustainer.operation.work().start()
    omniSustainer.sustain(sustainment1)
    delay(DELAY_MS)
    assertThat(operation1.isStarted()).isTrue()
  }

  @Test
  fun whenBecomesSustained_newCallsToControllerRelease_processedAsynchronously() = runBlocking {
    omniSustainer.operation.work().start()
    omniSustainer.sustain(sustainment1)
    delay(DELAY_MS)
    omniSustainer.release(sustainment1)
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStopped()).isTrue()
  }

  @Test
  fun whenBecomesSustained_newCallsToControllerReleaseAll_processedAsynchronously() = runBlocking {
    omniSustainer.operation.work().start()
    omniSustainer.sustain(sustainment1)
    delay(DELAY_MS)
    omniSustainer.releaseAll()
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStopped()).isTrue()
  }

  @Test
  fun whensustainmentStopped_sustainedObjectsAreAlsoStopped() = runBlocking {
    val operation = omniSustainer.operation.work().also { it.start() }
    omniSustainer.sustain(sustainment1)
    delay(DELAY_MS)
    operation.stop()
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStopped()).isTrue()
  }

  @Test
  fun whensustainmentStopped_newCallsToControllerSustain_haveNoEffect() = runBlocking {
    omniSustainer.operation.work().also {
      it.start()
      delay(DELAY_MS)
      it.stop()
      delay(DELAY_MS)
    }
    omniSustainer.sustain(sustainment1)
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenSustainMultipleSimulteneously_allAreSustained() = runBlocking {
    omniSustainer.operation.work().start()
    omniSustainer.sustain(sustainment1)
    omniSustainer.sustain(sustainment2)
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStarted()).isTrue()
    assertThat(operation2Started).isTrue()
  }

  companion object {
    private val DELAY_MS = 1000L
  }
}
