package com.jackbradshaw.codestone.lifecycle.orchestrator

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import kotlin.reflect.typeOf
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.WorkOrchestrators
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.StartStopOperation
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.startStopWorker
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
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
class WorkOrchestratorImplTest {

  private val workOrchestrator =
      WorkOrchestrators.create<Work<StartStop<StartStop<*, *>, Throwable>>>(
          StartStopOperation.WORK_TYPE,
          GlobalScope
      )

  private val operation1 = StartStopImpl<Unit, Throwable>()

  private val lifecycle1 = startStopWorker(operation1)

  private var operation2Started = false
  private val operation2 =
      GlobalScope.launch {
        operation2Started = true
        suspendCancellableCoroutine<Unit> { /* keep alive indefinitely */ }
      }

  private val lifecycle2 =
      object : Worker<Work<Job>> {
        override val work = object : Work<Job> {
          override val handle = operation2
          override val workType = typeOf<Job>()
        }
      }

  @Test
  fun whenNotSustained_callToControllerSustain_haveNEffect() {
    workOrchestrator.orchestrate(lifecycle1)
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenNotSustained_callToControllerRelease_haveNEffect() {
    workOrchestrator.orchestrate(lifecycle1)
    workOrchestrator.release(lifecycle1)
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenNotSustained_callToControllerReleaseAll_haveNoEffect() {
    workOrchestrator.orchestrate(lifecycle1)
    workOrchestrator.releaseAll()
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenBecomesSustained_prevlousCallToControllerSustain_processedAsynchronously() = runBlocking {
    workOrchestrator.orchestrate(lifecycle1)
    workOrchestrator.work.handle.start()
    delay(DELAY_MS)
    assertThat(operation1.isStarted()).isTrue()
  }

  @Test
  fun whenBecomesSustained_newCallsToControllerSustain_processedAsynchronously() = runBlocking {
    workOrchestrator.work.handle.start()
    workOrchestrator.orchestrate(lifecycle1)
    delay(DELAY_MS)
    assertThat(operation1.isStarted()).isTrue()
  }

  @Test
  fun whenBecomesSustained_newCallsToControllerRelease_processedAsynchronously() = runBlocking {
    workOrchestrator.work.handle.start()
    workOrchestrator.orchestrate(lifecycle1)
    delay(DELAY_MS)
    workOrchestrator.release(lifecycle1)
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStopped()).isTrue()
  }

  @Test
  fun whenBecomesSustained_newCallsToControllerReleaseAll_processedAsynchronously() = runBlocking {
    workOrchestrator.work.handle.start()
    workOrchestrator.orchestrate(lifecycle1)
    delay(DELAY_MS)
    workOrchestrator.releaseAll()
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStopped()).isTrue()
  }

  @Test
  fun whenlifecycleStopped_sustainedObjectsAreAlsoStopped() = runBlocking {
    val operation = workOrchestrator.work.handle.also { it.start() }
    workOrchestrator.orchestrate(lifecycle1)
    delay(DELAY_MS)
    operation.abort() // Changed from stop() to abort() as StartStop has no stop()
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStopped()).isTrue()
  }

  @Test
  fun whenlifecycleStopped_newCallsToControllerSustain_haveNoEffect() = runBlocking {
    workOrchestrator.work.handle.also {
      it.start()
      delay(DELAY_MS)
      it.abort() // Changed from stop() to abort()
      delay(DELAY_MS)
    }
    workOrchestrator.orchestrate(lifecycle1)
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isFalse()
  }

  @Test
  fun whenSustainMultipleSimulteneously_allAreSustained() = runBlocking {
    workOrchestrator.work.handle.start()
    workOrchestrator.orchestrate(lifecycle1)
    workOrchestrator.orchestrate(lifecycle2)
    delay(DELAY_MS)
    assertThat(operation1.wasStarted()).isTrue()
    assertThat(operation1.isStarted()).isTrue()
    assertThat(operation2Started).isTrue()
  }

  companion object {
    private val DELAY_MS = 1000L
  }
}

private fun StartStop<*, *>.wasStarted() = state.value.isPostStart
private fun StartStop<*, *>.isStarted() = state.value == com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState.Running
private fun StartStop<*, *>.isStopped() = state.value.isPostStop

