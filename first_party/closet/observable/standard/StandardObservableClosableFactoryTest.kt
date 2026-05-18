package com.jackbradshaw.closet.observable.standard

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

abstract class StandardObservableClosableFactoryTest {

  private val gates = mutableSetOf<CompletableDeferred<Unit>>()

  private val testScopeHandle = Job()

  protected val testScope by lazy { CoroutineScope(dispatcher() + testScopeHandle) }

  @After
  fun tearDown() {
    runBlocking {
      gates.forEach { it.complete(Unit) }
      testScopeHandle.cancelAndJoin()
    }
  }

  @Test
  fun createStandardClosable_returnsNewInstanceEachTime() =
      runBlocking<Unit> {
        val instance1 = factory().createStandardClosable()
        val instance2 = factory().createStandardClosable()

        assertThat(instance1).isNotSameInstanceAs(instance2)
      }

  @Test
  fun createStandardClosable_factoredValueClosedOnce_invokesClosableTaskOnce() =
      runBlocking<Unit> {
        val harness = factorClosable()

        harness.closable.close()

        assertThat(harness.closureStartedCount).isEqualTo(1)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  @Test
  fun createStandardClosable_factoredValueClosedSequentially_firstCompletes_secondNeverInvokesClosableTask() =
      runBlocking<Unit> {
        val harness = factorClosable()

        harness.closable.close()

        val closureStartedCountAfterFirst = harness.closureStartedCount
        val closureCompleteCountAfterFirst = harness.closureCompleteCount

        harness.closable.close()

        assertThat(closureStartedCountAfterFirst).isEqualTo(1)
        assertThat(closureCompleteCountAfterFirst).isEqualTo(1)
        assertThat(harness.closureStartedCount).isEqualTo(1)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  @Test
  fun createStandardClosable_factoredValueClosedSequentially_firstCancelledBeforeCompletion_secondResumesCancellation() =
      runBlocking<Unit> {
        val harness = factorSuspendingClosable()

        val firstAttempt = closeAndIdleAsync(harness.closable)
        firstAttempt.cancelAndJoin()

        closeAndIdleAsync(harness.closable)
        harness.gate.complete(Unit)
        taskBarrier().awaitAllIdle()

        assertThat(harness.closureStartedCount).isEqualTo(2)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  @Test
  fun createStandardClosable_factoredValueClosedSequentially_secondCompletes_thirdNeverInvokesClosableTask() =
      runBlocking<Unit> {
        val harness = factorSuspendingClosable()

        val firstAttempt = closeAndIdleAsync(harness.closable)
        firstAttempt.cancelAndJoin()

        closeAndIdleAsync(harness.closable)
        harness.gate.complete(Unit)
        taskBarrier().awaitAllIdle()

        val closureStartedCountAfterSecond = harness.closureStartedCount
        val closureCompleteCountAfterSecond = harness.closureCompleteCount

        closeAndIdleAsync(harness.closable)

        assertThat(closureStartedCountAfterSecond).isEqualTo(2)
        assertThat(closureCompleteCountAfterSecond).isEqualTo(1)
        assertThat(harness.closureStartedCount).isEqualTo(2)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  @Test
  fun createStandardClosable_factoredValueClosedSequentially_secondCancelledBeforeCompletion_thirdResumesCancellation() =
      runBlocking<Unit> {
        val harness = factorSuspendingClosable()

        val firstAttempt = closeAndIdleAsync(harness.closable)
        firstAttempt.cancelAndJoin()

        val secondAttempt = closeAndIdleAsync(harness.closable)
        secondAttempt.cancelAndJoin()

        closeAndIdleAsync(harness.closable)
        harness.gate.complete(Unit)
        taskBarrier().awaitAllIdle()

        assertThat(harness.closureStartedCount).isEqualTo(3)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  @Test
  fun createStandardClosable_factoredValueClosedConcurrently_firstCompletes_secondNeverInvokesClosableTask() =
      runBlocking<Unit> {
        val harness = factorClosable()

        testScope.launch { harness.closable.close() }
        testScope.launch { harness.closable.close() }
        taskBarrier().awaitAllIdle()

        assertThat(harness.closureStartedCount).isEqualTo(1)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  @Test
  fun createStandardClosable_factoredValueClosedConcurrently_firstCancelledBeforeCompletion_secondResumesCancellation() =
      runBlocking<Unit> {
        val harness = factorSuspendingClosable()

        val firstAttempt = closeAndIdleAsync(harness.closable)
        val secondAttempt = closeAndIdleAsync(harness.closable)

        firstAttempt.cancelAndJoin()
        taskBarrier().awaitAllIdle()
        harness.gate.complete(Unit)
        taskBarrier().awaitAllIdle()

        assertThat(harness.closureStartedCount).isEqualTo(2)
        assertThat(harness.closureCompleteCount).isEqualTo(1)
      }

  abstract fun factory(): StandardObservableClosableFactory

  abstract fun taskBarrier(): TestingTaskBarrier

  abstract fun dispatcher(): CoroutineDispatcher

  /** Creates a gate for pausing closure and registers it in [gates]. */
  private fun createGate() = CompletableDeferred<Unit>().also { gates.add(it) }

  /**
   * Launches an async task to execute `close` on [closable], waits for idle, and returns a [Job]
   * that completes when closing completes.
   */
  private suspend fun closeAndIdleAsync(closable: ObservableClosable): Job {
    val job = testScope.launch { closable.close() }
    taskBarrier().awaitAllIdle()
    return job
  }

  /**
   * Creates a closable via [factory] with a [close] function that returns immediately. The returned
   * [ClosureHarness] contains the closable and values for observing closure.
   */
  private suspend fun factorClosable(): ClosureHarness {
    val harness = ClosureHarness()
    harness.closable =
        factory().createStandardClosable {
          harness.closureStartedCount++
          harness.closureCompleteCount++
        }
    taskBarrier().awaitAllIdle()
    return harness
  }

  /**
   * Creates a closable via [factory] with a [close] function that suspends midway. The returned
   * [SuspendingClosureHarness] contains the closable and values for resuming/observing closure.
   */
  private suspend fun factorSuspendingClosable(): SuspendingClosureHarness {
    val harness = SuspendingClosureHarness()
    harness.closable =
        factory().createStandardClosable {
          harness.closureStartedCount++
          harness.gate.await()
          harness.closureCompleteCount++
        }
    taskBarrier().awaitAllIdle()
    return harness
  }

  /**
   * A [closable] created via [factory], with a [close] function that returns immediately, bundled
   * with values for observing closure execution.
   *
   * @property closable the created closable
   * @property closureStartedCount the number of times the `closure` block has started
   * @property closureCompleteCount the number of times the `closure` block has finished
   */
  protected inner class ClosureHarness {
    lateinit var closable: ObservableClosable
    var closureStartedCount = 0
    var closureCompleteCount = 0
  }

  /**
   * A [closable] created via [factory], with a [close] function that suspends until [gate] is
   * complete, bundled with values for observing closure execution.
   *
   * @property closable the created closable
   * @property gate controls the suspension of the [close] function during closure
   * @property closureStartedCount the number of times the `closure` block has started
   * @property closureCompleteCount the number of times the `closure` block has finished
   */
  protected inner class SuspendingClosureHarness {
    lateinit var closable: ObservableClosable
    var closureStartedCount = 0
    var closureCompleteCount = 0
    val gate = createGate()
  }
}
