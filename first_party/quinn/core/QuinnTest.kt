package com.jackbradshaw.quinn.core

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Abstract tests that all [Quinn] intances should pass.
 *
 * These tests cancel their jobs after the final assertions. This is necessary because `runBlocking`
 * will suspend until all child coroutines have finished, so the lingering jobs in the tests will
 * cause test timeouts if not cancelled. In some cases this is actually unnecesary, but the practice
 * is applied to all tests for consistency.
 */
@RunWith(JUnit4::class)
abstract class QuinnTest<T> {

  @After
  fun tearDown() {
    runBlocking { subject().close() }
  }

  /** Since `execute` does not call the block is not processed and should suspend. */
  @Test
  fun run_suspendsBeforeProcessing() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob = launch(cpuDispatcher()) { quinn.run { processed.add("first") } }
    taskBarrier().awaitAllIdle()

    assertThat(submitJob.isActive).isTrue()

    submitJob.cancel()
  }

  @Test
  fun run_resumesAfterProcessing() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob = launch(cpuDispatcher()) { quinn.run { processed.add("first") } }
    taskBarrier().awaitAllIdle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    assertThat(submitJob.isCompleted).isTrue()

    executeJob.cancelAndJoin()
  }

  @Test
  fun execute_betweenSubmissions_suspendsIndefinitely() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob1 = launch(cpuDispatcher()) { quinn.run { processed.add("first") } }
    taskBarrier().awaitAllIdle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add("second") } }
    taskBarrier().awaitAllIdle()

    // Check processed first to ensure execute job is active even after all values were processed
    assertThat(processed).containsExactly("first", "second")
    assertThat(executeJob.isActive).isTrue()

    submitJob1.cancelAndJoin()
    executeJob.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun execute_beforeSubmissions_suspendsIndefinitely() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob1 = launch(cpuDispatcher()) { quinn.run { processed.add("first") } }
    taskBarrier().awaitAllIdle()

    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add("second") } }
    taskBarrier().awaitAllIdle()

    // Check processed first to ensure execute job is active even after all values were processed
    assertThat(processed).containsExactly("first", "second")
    assertThat(executeJob.isActive).isTrue()

    submitJob1.cancelAndJoin()
    executeJob.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun execute_afterSubmissions_suspendsIndefinitely() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob1 = launch(cpuDispatcher()) { quinn.run { processed.add("first") } }
    taskBarrier().awaitAllIdle()

    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add("second") } }
    taskBarrier().awaitAllIdle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    // Check processed first to ensure execute job is active even after all values were processed
    assertThat(processed).containsExactly("first", "second")
    assertThat(executeJob.isActive).isTrue()

    submitJob1.cancelAndJoin()
    executeJob.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun multipleExecutes_sequentially_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(resource1) }
    val submitJob1 = launch(cpuDispatcher()) { quinn.run { processed.add(it) } }
    taskBarrier().awaitAllIdle()
    executeJob1.cancelAndJoin()

    val resource2 = createResource()
    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(resource2) }
    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add(it) } }
    taskBarrier().awaitAllIdle()
    executeJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()

    submitJob1.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun multipleExecutes_sequentially_activeExecuteResourceUsed() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val submitJob1 = launch(cpuDispatcher()) { quinn.run { processed.add(it) } }
    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(resource1) }
    taskBarrier().awaitAllIdle()
    executeJob1.cancelAndJoin()

    val resource2 = createResource()
    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add(it) } }
    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(resource2) }
    taskBarrier().awaitAllIdle()
    executeJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()

    submitJob1.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun multipleExecutes_sequentially_eachBlockEvaluatedOnce() = runBlocking {
    val quinn = subject()
    val evaluationCount = java.util.concurrent.atomic.AtomicInteger(0)

    val submitJob = launch(cpuDispatcher()) { quinn.run { evaluationCount.incrementAndGet() } }
    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()
    executeJob1.cancelAndJoin()

    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()
    executeJob2.cancelAndJoin()

    submitJob.cancelAndJoin()

    assertThat(evaluationCount.get()).isEqualTo(1)
  }

  @Test
  fun multipleExecutes_concurrently_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run { processed.add("first") }
          quinn.run { processed.add("second") }
        }
    taskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()
    executeJob2.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()

    submitJob.cancelAndJoin()
  }

  @Test
  fun multipleExecutes_concurrently_firstExecuteResourceUsed() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(resource1) }
    taskBarrier().awaitAllIdle()

    val resource2 = createResource()
    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(resource2) }
    taskBarrier().awaitAllIdle()

    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run { resource -> processed.add(resource) }
          quinn.run { resource -> processed.add(resource) }
        }
    taskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()
    executeJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource1).inOrder()

    submitJob.cancelAndJoin()
  }

  @Test
  fun multipleExecutes_concurrently_withCancellation_activeExecuteResourceUsed() =
      runBlocking {
        val quinn = subject()
        val processed = mutableListOf<T>()

        val resource1 = createResource()
        val executeJob1 = launch(cpuDispatcher()) { quinn.execute(resource1) }
        taskBarrier().awaitAllIdle()

        val resource2 = createResource()
        val executeJob2 = launch(cpuDispatcher()) { quinn.execute(resource2) }
        taskBarrier().awaitAllIdle()

        val submitJob1 =
            launch(cpuDispatcher()) { quinn.run { resource -> processed.add(resource) } }
        taskBarrier().awaitAllIdle()

        executeJob1.cancelAndJoin()
        taskBarrier().awaitAllIdle()

        val submitJob2 =
            launch(cpuDispatcher()) { quinn.run { resource -> processed.add(resource) } }
        taskBarrier().awaitAllIdle()

        assertThat(processed).containsExactly(resource1, resource2).inOrder()

        submitJob1.cancelAndJoin()
        submitJob2.cancelAndJoin()
        executeJob2.cancelAndJoin()
      }

  @Test
  fun multipleExecutes_concurrently_allExecutesSuspend() = runBlocking {
    val quinn = subject()

    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    assertThat(executeJob1.isActive).isTrue()
    assertThat(executeJob2.isActive).isTrue()

    executeJob1.cancel()
    executeJob2.cancel()
  }

  @Test
  fun submitThenExecute_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run { processed.add("first") }
          quinn.run { processed.add("second") }
        }
    taskBarrier().awaitAllIdle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()

    submitJob.cancelAndJoin()
  }

  @Test
  fun executeThenSubmit_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()
    
    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run { processed.add("first") }
          quinn.run { processed.add("second") }
        }
    taskBarrier().awaitAllIdle()
    executeJob.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()

    submitJob.cancelAndJoin()
  }

  @Test
  fun multipleExecutes_concurrently_eachBlockEvaluatedOnce() = runBlocking {
    val quinn = subject()
    val evaluations = java.util.concurrent.atomic.AtomicInteger(0)

    val executeJob1 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    val executeJob2 = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob = launch(cpuDispatcher()) { quinn.run { evaluations.incrementAndGet() } }
    taskBarrier().awaitAllIdle()

    quinn.close()
    executeJob1.join()
    executeJob2.join()
    submitJob.join()

    assertThat(evaluations.get()).isEqualTo(1)
  }

  @Test
  fun close_premptsProcessingPendingBlocks(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()
    val pauseHandle = TestingSuspensionController()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }

    val submitJob1 =
        launch(cpuDispatcher()) {
          quinn.run {
            pauseHandle.suspend()
            processed.add("first")
          }
        }
    taskBarrier().awaitAllIdle()

    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add("second") } }

    val closeJob = launch(cpuDispatcher()) { quinn.close() }

    // See comment on DELAY_DURATION_MS.
    kotlinx.coroutines.delay(DELAY_DURATION_MS)

    pauseHandle.resume()
    taskBarrier().awaitAllIdle()

    submitJob1.join()
    submitJob2.join()
    closeJob.join()
    executeJob.join()

    assertThat(processed).containsExactly("first")
  }

  @Test
  fun close_allowsActiveBlocksToComplete(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()
    val pauseHandle = TestingSuspensionController()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }

    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run {
            pauseHandle.suspend()
            processed.add("first")
          }
        }

    taskBarrier().awaitAllIdle()

    val closeJob = launch(cpuDispatcher()) { quinn.close() }

    // See comment on DELAY_DURATION_MS.
    kotlinx.coroutines.delay(DELAY_DURATION_MS)

    pauseHandle.resume()

    submitJob.join()
    closeJob.join()
    executeJob.join()

    assertThat(processed).containsExactly("first")
  }

  @Test
  fun afterClose_run_fails() = runBlocking {
    val quinn = subject()
    quinn.close()

    val error = assertFailsWith<IllegalStateException> { quinn.run {} }

    assertThat(error.message).isEqualTo("This Quinn instance is closed, run cannot be used.")
  }

  @Test
  fun afterClose_execute_doesNotFail() = runBlocking {
    val quinn = subject()

    quinn.close()

    quinn.execute(createResource())
  }

  @Test
  fun afterClose_tryRun_returnsFalse() = runBlocking {
    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryRun {}

    assertThat(tryResult).isFalse()
  }

  @Test
  fun tryRun_returnsTrueWhenActive() = runBlocking {
    val quinn = subject()

    var success = false
    val submitJob = launch(cpuDispatcher()) { success = quinn.tryRun {} }
    taskBarrier().awaitAllIdle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    assertThat(success).isTrue()

    submitJob.cancelAndJoin()
    executeJob.cancelAndJoin()
  }

  abstract fun subject(): Quinn<T>

  abstract fun cpuDispatcher(): CoroutineDispatcher

  abstract fun taskBarrier(): TestingTaskBarrier

  /**
   * Creates a new resource to be supplied to Quinn. An equals-identify unique value must be
   * returned on each call. Does not require thread safety.
   */
  abstract fun createResource(): T

  companion object {
    /**
     * The duration to delay for in tests that evaluate closure behaviour.
     * 
     * This is necessary because `close` is a blocking call so any coroutine interactions it does
     * must be wrapped with `runBlocking`. Any usage of `runBlocking` inherently prevents an idle
     * state because `runBlocking` does not yield the thread back to the dispatcher until all its
     * work completes.
     * 
     * TODO(jack-bradshaw): Create a suspendable closure interface so close can be non-blocking.
     */
    private const val DELAY_DURATION_MS = 50L
  }


  /**
   * Handle to precisely and deterministically pause and resume a coroutine within a test.
   *
   * Example:
   * ```kotlin
   * val pauseHandle = TestingSuspensionController()
   *
   * val executionJob = launch(cpuDispatcher()) {
   *   pauseHandle.suspend()
   *   // Execution is deferred at this point...
   * }
   *
   * // Does not proceed until suspension has occurred.
   * taskBarrier().awaitAllIdle()
   *
   * pauseHandle.resume()
   * // Execution of the launched block now resumes.
   * ```
   *
   * This is useful when tests need to start work then suspend it until a specific condition has
   * been met, which is a common scenario when testing the edge cases of a concurrent system, as
   * such scenarios are where race conditions and complex multi-threading issues emerge.
   */
  protected class TestingSuspensionController {
    
    /** Whether suspension has started */
    private val suspendStarted = kotlinx.coroutines.CompletableDeferred<Unit>()

    /** Whether suspension has resumed. Should not complete before [suspendStarted]. */
    private val suspendCompleted = kotlinx.coroutines.CompletableDeferred<Unit>()

    /** Suspends until [resume] is called. */
    suspend fun suspend() {
      suspendStarted.complete(Unit)
      suspendCompleted.await()
    }

    /** Suspends until [suspend] has been invoked natively. */
    suspend fun waitUntilSuspended() {
      suspendStarted.await()
    }

    /** Resumes anything waiting for suspension to end. */
    fun resume() {
      suspendCompleted.complete(Unit)
    }
  }
}