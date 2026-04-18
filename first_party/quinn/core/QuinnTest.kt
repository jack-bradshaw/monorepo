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

  @Test
  fun submit_suspendsBeforeProcessing() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob = launch(cpuDispatcher()) { quinn.run { processed.add("first") } }
    taskBarrier().awaitAllIdle()

    assertThat(submitJob.isActive).isTrue()

    submitJob.cancel()
  }

  @Test
  fun submit_resumesAfterProcessing() = runBlocking {
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
  fun execute_suspendsIndefinitely() = runBlocking {
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
  fun multipleExecutes_sequentially_invokesAllBlocksInSubmissionOrder() = runBlocking {
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
  fun multipleExecutes_sequentially_firstExecuteResourceUsed() = runBlocking {
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
  fun multipleExecutes_concurrently_resourceFromSecondExecuteUsedAfterFirstCancelled() =
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
  fun concurrent_execute_eachBlockEvaluatedOnce() = runBlocking {
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
  fun afterAllBlocksInvoked_executeRemainsSuspended() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run { processed.add(it) }
          quinn.run { processed.add(it) }
        }
    taskBarrier().awaitAllIdle()

    assertThat(executeJob.isActive).isTrue()

    executeJob.cancel()
    submitJob.cancelAndJoin()
  }

  @Test
  fun close_premptsProcessingPendingBlocks(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()
    val pauseHandle = TestingPauseHandle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }

    val submitJob1 =
        launch(cpuDispatcher()) {
          quinn.run {
            pauseHandle.pause()
            processed.add("first")
          }
        }

    pauseHandle.waitUntilPaused()

    val submitJob2 = launch(cpuDispatcher()) { quinn.run { processed.add("second") } }

    val closeJob = launch(cpuDispatcher()) { quinn.close() }

    pauseHandle.resume()

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
    val pauseHandle = TestingPauseHandle()

    val executeJob = launch(cpuDispatcher()) { quinn.execute(createResource()) }

    val submitJob =
        launch(cpuDispatcher()) {
          quinn.run {
            pauseHandle.pause()
            processed.add("first")
          }
        }

    pauseHandle.waitUntilPaused()

    val closeJob = launch(cpuDispatcher()) { quinn.close() }

    pauseHandle.resume()

    submitJob.join()
    closeJob.join()
    executeJob.join()

    assertThat(processed).containsExactly("first")
  }

  @Test
  fun afterClose_submit_fails() = runBlocking {
    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryRun {}

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
  fun afterClose_tryQueue_returnsFalse() = runBlocking {
    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryRun {}

    assertThat(tryResult).isFalse()
  }

  @Test
  fun tryQueue_returnsTrueWhenActive() = runBlocking {
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

  // TODO(jack@jack-bradshaw.com): Extract to a separate package for reusability.
  /**
   * Handle to precisely and deterministically pause and resume a coroutine within a test.
   *
   * Example:
   * ```kotlin
   * val pauseHandle = TestingPauseHandle()
   *
   * val executionJob = launch(cpuDispatcher()) {
   *   pauseHandle.pause()
   *   // Execution is deferred at this point...
   * }
   *
   * pauseHandle.waitUntilPaused()
   * // It is now guaranteed the coroutine is suspended actively mid-evaluation.
   * // External synchronization or external event invocations can happen here safely.
   *
   * pauseHandle.resume()
   * // Execution is unrestrained and concludes.
   * ```
   *
   * This is useful when tests need to start work then suspend it until a specific condition has
   * been met, which is a common scenario when testing the edge cases of a concurrent system, as
   * such scenarios are where race conditions and complex multi-threading issues emerge.
   */
  protected class TestingPauseHandle {
    /** Whether pause has started */
    private val pauseStarted = kotlinx.coroutines.CompletableDeferred<Unit>()

    /** Whether pause has resumed. Should not complete before [pauseStarted]. */
    private val pauseCompleted = kotlinx.coroutines.CompletableDeferred<Unit>()

    /** Pauses and suspends until [resume] is called. */
    fun pause() {
      pauseStarted.complete(Unit)
      runBlocking { pauseCompleted.await() }
    }

    /** Suspends until [pause] has been invoked. */
    suspend fun waitUntilPaused() {
      pauseStarted.await()
    }

    /** Resumes anything waiting for pause to end. */
    fun resume() {
      pauseCompleted.complete(Unit)
    }
  }
}
