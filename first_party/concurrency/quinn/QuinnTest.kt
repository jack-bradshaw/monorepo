

package com.jackbradshaw.concurrency.quinn

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.Quinn.ErrorHandling
import com.jackbradshaw.concurrency.quinn.Quinn.InsertionResult
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch

import kotlin.test.assertFailsWith
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler

import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4




/**
 * Abstract tests that all [Quinn] instances should pass.
 *
 * These tests verify:
 * 
 * 1. Basic queueing and suspension mechanics: Verifies that submission functions (`queueAtBack`,
 *    `queueAtFront`, `tryQueueAtBack`, `tryQueueAtFront`) suspend while the task is pending, and
 *    resume once the task is evaluated.
 * 2. Basic execution mechanics: Ensures that `execute()` suspends indefinitely while waiting for
 *    work to arrive, without returning prematurely when the queue is
 *    empty.
 * 3. Execution evaluation flows: Validates the fundamental end-to-end pathway, ensuring that tasks
 *    queued prior to execution are processed once `execute()` is called, and tasks queued while an
 *    executor is already active are processed immediately.
 * 4. Concurrent / sequential executor overlap: Verifies system stability when multiple `execute()`
 *    calls are active either sequentially or concurrently. Ensures that the oldest active resource
 *    is consistently prioritized, tasks are never duplicated or dropped, and newly launched
 *    executors seamlessly assume responsibility if the active executor is cancelled.
 * 5. Execution state observability: Ensures the `isExecuting` StateFlow property accurately reports
 *    `true` while any coroutine is actively suspending inside `execute()`, gracefully scaling to
 *    track multiple concurrent or sequential executor overlaps, and strictly returning `false` only
 *    when all executors have fully terminated.
 * 6. Closure and shutdown semantics: Asserts the rigorous teardown protocol initiated by `close()`.
 *    Verifies that actively running tasks finish gracefully, pending tasks in the queue are
 *    safely evicted and their submission coroutines resumed, subsequent submissions are strictly
 *    rejected, and the executor loop exits reliably without error.
 * 7. Error handling and propagation: Verifies that exceptions thrown within queued tasks are
 *    correctly routed based on `ErrorHandling`. Ensures `DELIVER_TO_SUBMISSION_SIDE` safely
 *    propagates the exception back to the submitting coroutine without destabilizing the executor,
 *    while `DELIVER_TO_EXECUTION_SIDE` allows the exception to surface naturally on the execution
 *    thread.

 */
@RunWith(JUnit4::class)
abstract class QuinnTest<T> {


  private val latches = ConcurrentHashMap.newKeySet<CountDownLatch>()

  private val subjectLinkedScopeHandle = Job()

  private val subjectIndependentScopeHandle = Job()

  private val subjectLinkedScope by lazy {
    CoroutineScope(subjectLinkedDispatcher() + subjectLinkedScopeHandle)
  }

  private val subjectIndependentScope by lazy {
    CoroutineScope(subjectIndependentDispatcher() + subjectIndependentScopeHandle)
  }



  @After
  fun tearDown() {
    latches.forEach { it.countDown() }
    runBlocking {
      subject().close()

      subjectLinkedScopeHandle.cancel()
      subjectIndependentScopeHandle.cancel()
    }
  }

  /** Returns the subject under test. Must return the same instance each time. */
  abstract fun subject(): Quinn<T>

  /** Returns a dispatcher configured for CPU bound work. */
  abstract fun subjectLinkedDispatcher(): CoroutineDispatcher

  /** Returns a task barrier linked to [cpuDispatcher]. */
  abstract fun subjectLinkedTaskBarrier(): TestingTaskBarrier

  /**
   * A secondary CPU dispatcher. Must be strictly distinct from [cpuDispatcher] to ensure
   * independent task barrier tracking. Only used for tests that intentionally block threads.
   */
  abstract fun subjectIndependentDispatcher(): CoroutineDispatcher

  /** The task barrier associated with [cpuDispatcher2]. */
  abstract fun subjectIndependentTaskBarrier(): TestingTaskBarrier

  /**
   * Creates a new resource that can be supplied to [Quinn]. A new value must be returned on each
   * call (i.e. not equals-identical to any of the previous instances).
   */
  abstract fun createResource(): T

  @Test
  fun queueAtBack_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtBack { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun queueAtFront_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtFront { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun queueAtBack_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()

    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtBack { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test

  fun queueAtFront_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtFront { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test
  fun tryQueueAtBack_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()

    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.tryQueueAtBack { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun tryQueueAtFront_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.tryQueueAtFront { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun tryQueueAtBack_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()

    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.tryQueueAtBack { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test

  fun tryQueueAtFront_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.tryQueueAtFront { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test
  fun execute_beforeQueueing_suspendsIndefinitely(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(executeJob.isActive).isTrue()
  }

  @Test
  fun execute_betweenQueueing_suspendsIndefinitely(): Unit = runBlocking {
    val quinn = subject()

    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(executeJob.isActive).isTrue()
  }

  @Test
  fun execute_afterQueueing_suspendsIndefinitely(): Unit = runBlocking {
    val quinn = subject()

    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(executeJob.isActive).isTrue()
  }

  @Test
  fun multipleExecutes_sequentially_allBlocksComplete(): Unit = runBlocking {
    val quinn = subject()

    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val executeJob1 = subjectLinkedScope.launch { quinn.execute(resource1) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack { processed.add(it) } }
    subjectLinkedTaskBarrier().awaitAllIdle()
    executeJob1.cancelAndJoin()

    val resource2 = createResource()
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(resource2) }
    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack { processed.add(it) } }
    subjectLinkedTaskBarrier().awaitAllIdle()
    executeJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()

  }

  @Test
  fun multipleExecutes_sequentially_activeExecuteResourceUsed(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()

    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack { processed.add(it) } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob1 = subjectLinkedScope.launch { quinn.execute(resource1) }
    subjectLinkedTaskBarrier().awaitAllIdle()
    executeJob1.cancelAndJoin()

    val resource2 = createResource()
    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack { processed.add(it) } }
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(resource2) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(processed).containsExactly(resource1, resource2).inOrder()
  }

  @Test
  fun multipleExecutes_sequentially_eachBlockEvaluatedOnce(): Unit = runBlocking {
    val quinn = subject()

    val evaluationCount = java.util.concurrent.atomic.AtomicInteger(0)

    val queueJob =
        subjectLinkedScope.launch { quinn.queueAtBack { evaluationCount.incrementAndGet() } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()
    executeJob1.cancelAndJoin()

    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(evaluationCount.get()).isEqualTo(1)
  }

  @Test
  fun multipleExecutes_concurrently_allBlocksComplete(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()


    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob =
        subjectLinkedScope.launch {
          quinn.queueAtBack { processed.add("first") }
          quinn.queueAtBack { processed.add("second") }
        }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(processed).containsExactly("first", "second").inOrder()
  }

  @Test
  fun multipleExecutes_concurrently_firstNeverCancelled_firstUsedAlways(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()

    val executeJob1 = subjectLinkedScope.launch { quinn.execute(resource1) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val resource2 = createResource()
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(resource2) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob =
        subjectLinkedScope.launch {
          quinn.queueAtBack { resource -> processed.add(resource) }
          quinn.queueAtBack { resource -> processed.add(resource) }
        }
    subjectLinkedTaskBarrier().awaitAllIdle()

    // Asserting which resource was used effectively asserts which executor processed the task
    assertThat(processed).containsExactly(resource1, resource1).inOrder()
  }

  @Test
  fun multipleExecutes_concurrently_firstCancelled_firstThenSecondUsed(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()

    val executeJob1 = subjectLinkedScope.launch { quinn.execute(resource1) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val resource2 = createResource()
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(resource2) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob1 =
        subjectLinkedScope.launch { quinn.queueAtBack { resource -> processed.add(resource) } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 =
        subjectLinkedScope.launch { quinn.queueAtBack { resource -> processed.add(resource) } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    // Asserting which resource was used effectively asserts which executor processed the task
    assertThat(processed).containsExactly(resource1, resource2).inOrder()
  }

  @Test
  fun multipleExecutes_beforeQueueing_bothSuspend(): Unit = runBlocking {
    val quinn = subject()


    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(executeJob1.isActive).isTrue()
    assertThat(executeJob2.isActive).isTrue()
  }

  @Test
  fun multipleExecutes_betweenQueueing_bothSuspend(): Unit = runBlocking {
    val quinn = subject()


    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(executeJob1.isActive).isTrue()
    assertThat(executeJob2.isActive).isTrue()
  }

  @Test
  fun multipleExecutes_afterQueueing_bothSuspend(): Unit = runBlocking {
    val quinn = subject()


    val queueJob1 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob2 = subjectLinkedScope.launch { quinn.queueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(executeJob1.isActive).isTrue()
    assertThat(executeJob2.isActive).isTrue()
  }

  @Test
  fun isExecuting_neverExecuted_isFalse(): Unit = runBlocking {
    val quinn = subject()


    assertThat(quinn.isExecuting.value).isFalse()
  }

  @Test
  fun isExecuting_singleExecution_isTrue(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test

  fun isExecuting_singleExecutionStops_isFalse(): Unit = runBlocking {
    val quinn = subject()
    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isFalse()
  }

  @Test

  fun isExecuting_multipleParallelExecutions_isTrue(): Unit = runBlocking {
    val quinn = subject()
    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test

  fun isExecuting_multipleParallelExecutions_firstEnds_isTrue(): Unit = runBlocking {
    val quinn = subject()
    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()
    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun isExecuting_multipleSequentialExecutions_isTrue(): Unit = runBlocking {
    val quinn = subject()
    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test

  fun isExecuting_multipleSequentialExecutions_firstEnds_isTrue(): Unit = runBlocking {
    val quinn = subject()
    val executeJob1 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()
    val executeJob2 = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun queueThenExecute_atBack_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtBack { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun queueThenExecute_atFront_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtFront { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun executeThenQueue_atBack_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtBack { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun executeThenQueue_atFront_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val queueJob = subjectLinkedScope.launch { quinn.queueAtFront { processed.add("first") } }
    subjectLinkedTaskBarrier().awaitAllIdle()


    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test

  fun close_finishesCurrentBlockAndIgnoresPendingBlocks(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()
    val pauseHandle = newLatch()
    val startedExecution = MutableStateFlow(false)

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }

    val queueJob1 =
        subjectIndependentScope.launch {
          quinn.queueAtBack {
            startedExecution.value = true
            pauseHandle.await()
            processed.add("first")
          }
        }
    startedExecution.first { it }

    val queueJob2 = subjectIndependentScope.launch { quinn.queueAtBack { processed.add("second") } }
    subjectIndependentTaskBarrier().awaitAllIdle()

    val closeJob = subjectLinkedScope.launch { quinn.close() }
    // View comment on DELAY_DURATION_MS for details.
    delay(DELAY_DURATION_MS)

    pauseHandle.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first")
  }

  @Test
  fun queueAtBack_afterClose_fails(): Unit = runBlocking {

    val quinn = subject()
    quinn.close()

    val error = assertFailsWith<IllegalStateException> { quinn.queueAtBack {} }

    assertThat(error.message)
        .isEqualTo("This Quinn instance is closed, queueAtBack cannot be used.")
  }

  @Test

  fun tryQueueAtBack_executesCompletely_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val result = subjectLinkedScope.async { quinn.tryQueueAtBack {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun tryQueueAtBack_queuedBeforeClose_pendingAtClose_returnsInsertedNotRun(): Unit = runBlocking {
    val quinn = subject()
    val pauseHandle = newLatch()
    val startedExecution = MutableStateFlow(false)

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }

    val queueJob1 =
        subjectIndependentScope.launch {
          quinn.queueAtBack {
            startedExecution.value = true
            pauseHandle.await()
          }
        }
    startedExecution.first { it }

    val result = subjectIndependentScope.async { quinn.tryQueueAtBack {} }
    subjectIndependentTaskBarrier().awaitAllIdle()

    val closeJob = subjectLinkedScope.launch { quinn.close() }
    // View comment on DELAY_DURATION_MS for details.
    delay(DELAY_DURATION_MS)

    pauseHandle.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_NOT_RUN)
  }

  @Test
  fun tryQueueAtBack_afterClose_returnsRejectedClosed(): Unit = runBlocking {

    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryQueueAtBack {}


    assertThat(tryResult).isEqualTo(Quinn.InsertionResult.REJECTED_CLOSED)

  }

  @Test // todo gemini propagate name changes to other variants
  fun tryQueueAtFront_executesCompletely_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val result = subjectLinkedScope.async { quinn.tryQueueAtFront {} }
    subjectLinkedTaskBarrier().awaitAllIdle()

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  // gemini side note: iterable quin testshould also pass these tests. use the approach elsewhere to
  // add a seconary test fo rit (like how we test things follow observableclosable contrct)
  // add to impl plan

  @Test
  fun tryQueueAtFront_queuedBeforeClose_pendingAtClose_returnsInsertedNotRun(): Unit = runBlocking {
    val quinn = subject()
    val pauseHandle = newLatch()
    val startedExecution = MutableStateFlow(false)

    val executeJob = subjectLinkedScope.launch { quinn.execute(createResource()) }
    // test scopes also need to be named subjet independent and subjedt linked
    val queueJob1 =
        subjectIndependentScope.launch {
          quinn.queueAtBack {
            startedExecution.value = true
            pauseHandle.await()
          }
        }
    startedExecution.first { it }

    val result = subjectIndependentScope.async { quinn.tryQueueAtFront {} }
    subjectIndependentTaskBarrier().awaitAllIdle()

    val closeJob = subjectLinkedScope.launch { quinn.close() }
    // View comment on DELAY_DURATION_MS for details.
    delay(DELAY_DURATION_MS)

    pauseHandle.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()

    assertThat(result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_NOT_RUN)
  }

  @Test
  fun tryQueueAtFront_queuedAfterClose_returnsRejectedClosed(): Unit = runBlocking {

    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryQueueAtFront {}


    assertThat(tryResult).isEqualTo(Quinn.InsertionResult.REJECTED_CLOSED)

  }

  @Test
  fun execute_afterClose_doesNotFail(): Unit = runBlocking {
    val quinn = subject()

    quinn.close()

    quinn.execute(createResource())
  }

  @Test
  fun queueAtBack_taskFails_deliverToCallerMode_throwsExceptionSubmissionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()

        val executionSideError = executeAsyncWithCaughtError(quinn)
        val error = IllegalStateException("Foo")
        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.queueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        subjectLinkedTaskBarrier().awaitAllIdle()

        assertThat(executionSideError.isCompleted).isFalse()

        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun queueAtBack_taskFails_deliverToCallerMode_executorRemainsOperational(): Unit = runBlocking {
    val quinn = subject()

    val executionSideError = executeAsyncWithCaughtError(quinn)
    try {
      quinn.queueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) {
        throw IllegalStateException("Foo")
      }
    } catch (e: IllegalStateException) {
      // Expected
    }

    var ran = false
    quinn.queueAtBack { ran = true }
    subjectLinkedTaskBarrier().awaitAllIdle()

    // Running implies the system still works despite the failure
    assertThat(ran).isTrue()
  }

  @Test
  fun queueAtBack_taskFails_deliverToExecutorMode_throwsExceptionExecutionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val executionSideError = executeAsyncWithCaughtError(quinn)

        quinn.queueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        subjectLinkedTaskBarrier().awaitAllIdle()

        // Implicit assertion: No exception occured submission side if here.
        assertThat(executionSideError.await()).isEqualTo(error)
      }

  @Test
  fun queueAtFront_taskFails_deliverToCallerMode_throwsExceptionSubmissionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()

        val executionSideError = executeAsyncWithCaughtError(quinn)
        val error = IllegalStateException("Foo")
        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.queueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        subjectLinkedTaskBarrier().awaitAllIdle()

        assertThat(executionSideError.isCompleted).isFalse()
        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun queueAtFront_taskFails_deliverToCallerMode_executorRemainsOperational(): Unit = runBlocking {
    val quinn = subject()

    val executionSideError = executeAsyncWithCaughtError(quinn)
    try {
      quinn.queueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) {
        throw IllegalStateException("Foo")
      }
    } catch (e: IllegalStateException) {
      // Expected
    }

    var ran = false
    quinn.queueAtFront { ran = true }
    subjectLinkedTaskBarrier().awaitAllIdle()

    // Running implies the system still works despite the failure
    assertThat(ran).isTrue()
  }

  @Test
  fun queueAtFront_taskFails_deliverToExecutorMode_throwsExceptionExecutionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val executionSideError = executeAsyncWithCaughtError(quinn)

        quinn.queueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        subjectLinkedTaskBarrier().awaitAllIdle()

        // Implicit assertion: No exception occured submission side if here.
        assertThat(executionSideError.await()).isEqualTo(error)
      }

  @Test
  fun tryQueueAtBack_taskFails_deliverToCallerMode_throwsExceptionSubmissionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()

        val executionSideError = executeAsyncWithCaughtError(quinn)
        val error = IllegalStateException("Foo")
        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        subjectLinkedTaskBarrier().awaitAllIdle()

        assertThat(executionSideError.isCompleted).isFalse()
        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun tryQueueAtBack_taskFails_deliverToCallerMode_executorRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()

        val executionSideError = executeAsyncWithCaughtError(quinn)
        try {
          quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) {
            throw IllegalStateException("Foo")
          }
        } catch (e: IllegalStateException) {
          // Expected
        }

        var ran = false
        quinn.tryQueueAtBack { ran = true }
        subjectLinkedTaskBarrier().awaitAllIdle()

        // Running implies the system still works despite the failure

        assertThat(ran).isTrue()
      }

  @Test
  fun tryQueueAtBack_taskFails_deliverToExecutorMode_throwsExceptionExecutionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val executionSideError = executeAsyncWithCaughtError(quinn)

        quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        subjectLinkedTaskBarrier().awaitAllIdle()

        // Implicit assertion: No exception occured submission side if here.
        assertThat(executionSideError.await()).isEqualTo(error)
      }

  @Test
  fun tryQueueAtFront_taskFails_deliverToCallerMode_throwsExceptionSubmissionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()

        val executionSideError = executeAsyncWithCaughtError(quinn)
        val error = IllegalStateException("Foo")
        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        subjectLinkedTaskBarrier().awaitAllIdle()

        assertThat(executionSideError.isCompleted).isFalse()
        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun tryQueueAtFront_taskFails_deliverToCallerMode_executorRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()

        val executionSideError = executeAsyncWithCaughtError(quinn)
        try {
          quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) {
            throw IllegalStateException("Foo")
          }
        } catch (e: IllegalStateException) {
          // Expected
        }

        var ran = false
        quinn.tryQueueAtFront { ran = true }
        subjectLinkedTaskBarrier().awaitAllIdle()

        // Running implies the system still works despite the failure

        assertThat(ran).isTrue()
      }

  @Test
  fun tryQueueAtFront_taskFails_deliverToExecutorMode_throwsExceptionExecutionSideOnly(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val executionSideError = executeAsyncWithCaughtError(quinn)

        quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        subjectLinkedTaskBarrier().awaitAllIdle()

        // Implicit assertion: No exception occured submission side if here.
        assertThat(executionSideError.await()).isEqualTo(error)
      }

  /**
   * Launches a job to run `execute` on [quinn] and returns a [Deferred] that completes with the
   * error thrown by `execute` if any, or `null` if `execute` returns without error.
   */
  private suspend fun executeAsyncWithCaughtError(quinn: Quinn<T>): Deferred<Throwable?> {
    val exception = CompletableDeferred<Throwable?>()
    val exceptionCatcher = CoroutineExceptionHandler { _, e -> exception.complete(e) }
    subjectLinkedScope.launch(exceptionCatcher) {
      quinn.execute(createResource())
      exception.complete(null)
    }
    subjectLinkedTaskBarrier().awaitAllIdle()
    return exception
  }

  private fun newLatch(): CountDownLatch {
    val latch = java.util.concurrent.CountDownLatch(1)
    latches.add(latch)
    return latch
  }



  companion object {
    /**
     * The duration to delay when waiting for closure to reach a suspending point.
     *
     * This is necessary because `close` is a blocking call, so any coroutine interactions it does
     * must be wrapped with `runBlocking, which inherently prevents use of the task barrier.
     *
     * TODO(jack-bradshaw): Create a suspendable closure interface so close can be non-blocking.
     */
    private const val DELAY_DURATION_MS = 50L
  }
}
