package com.jackbradshaw.concurrency.quinn

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.Quinn.ErrorHandling
import com.jackbradshaw.concurrency.quinn.Quinn.InsertionResult
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Abstract tests that all [Quinn] instances should pass.
 *
 * This test requires two separate coroutine scopes and task barriers because they require two
 * independent pairs of execution/idling. This occurs because the system running `execute` never
 * reaches an idle state.
 */
@RunWith(JUnit4::class)
abstract class QuinnTest<T> {

  /** All latches created during testing. Collected for automatic closure. */
  private val latches = ConcurrentHashMap.newKeySet<CountDownLatch>()

  private val scope1Handle = Job()

  private val scope1 by lazy { CoroutineScope(scope1Dispatcher() + scope1Handle) }

  private val scope2Handle = Job()

  private val scope2 by lazy { CoroutineScope(scope2Dispatcher() + scope2Handle) }

  @After
  fun tearDown() {
    latches.forEach { it.countDown() }
    runBlocking {
      subject().close()
      scope1Handle.cancelAndJoin()
      scope2Handle.cancelAndJoin()
    }
  }

  @Test
  fun queueAtBack_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun queueAtBack_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test
  fun queueAtFront_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.queueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun queueAtFront_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.queueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test
  fun tryQueueAtBack_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.tryQueueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun tryQueueAtBack_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.tryQueueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test
  fun tryQueueAtFront_suspendsBeforeProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.tryQueueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isActive).isTrue()
  }

  @Test
  fun tryQueueAtFront_resumesAfterProcessing(): Unit = runBlocking {
    val quinn = subject()

    val queueJob = scope1.launch { quinn.tryQueueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob.isCompleted).isTrue()
  }

  @Test
  fun execute_beforeQueueing_suspendsIndefinitely(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob1 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(executeJob.isActive).isTrue()
  }

  @Test
  fun execute_betweenQueueing_suspendsIndefinitely(): Unit = runBlocking {
    val quinn = subject()

    val queueJob1 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(executeJob.isActive).isTrue()
  }

  @Test
  fun execute_afterQueueing_suspendsIndefinitely(): Unit = runBlocking {
    val quinn = subject()

    val queueJob1 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(executeJob.isActive).isTrue()
  }

  @Test
  fun multipleExecutes_sequentially_allBlocksComplete(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob1 = scope1.launch { quinn.queueAtBack { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack { processed.add("second") } }
    scope1TaskBarrier().awaitAllIdle()

    executeJob2.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()
  }

  @Test
  fun multipleExecutes_sequentially_activeExecuteResourceUsed(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val queueJob1 = scope1.launch { quinn.queueAtBack { processed.add(it) } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob1 = scope1.launch { quinn.execute(resource1) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    val resource2 = createResource()
    val queueJob2 = scope1.launch { quinn.queueAtBack { processed.add(it) } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob2 = scope1.launch { quinn.execute(resource2) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()
  }

  @Test
  fun multipleExecutes_sequentially_eachBlockEvaluatedOnce(): Unit = runBlocking {
    val quinn = subject()
    val evaluationCount = AtomicInteger(0)

    val queueJob = scope1.launch { quinn.queueAtBack { evaluationCount.incrementAndGet() } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(evaluationCount.get()).isEqualTo(1)
  }

  @Test
  fun multipleExecutes_concurrently_allBlocksComplete(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob =
        scope1.launch {
          quinn.queueAtBack { processed.add("first") }
          quinn.queueAtBack { processed.add("second") }
        }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first", "second").inOrder()
  }

  @Test
  fun multipleExecutes_concurrently_firstNeverCancelled_firstUsedAlways(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val executeJob1 = scope1.launch { quinn.execute(resource1) }
    scope1TaskBarrier().awaitAllIdle()

    val resource2 = createResource()
    val executeJob2 = scope1.launch { quinn.execute(resource2) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob =
        scope1.launch {
          quinn.queueAtBack { resource -> processed.add(resource) }
          quinn.queueAtBack { resource -> processed.add(resource) }
        }
    scope1TaskBarrier().awaitAllIdle()

    // Asserting which resource was used effectively asserts which executor processed the task
    assertThat(processed).containsExactly(resource1, resource1).inOrder()
  }

  @Test
  fun multipleExecutes_concurrently_firstCancelled_firstThenSecondUsed(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val executeJob1 = scope1.launch { quinn.execute(resource1) }
    scope1TaskBarrier().awaitAllIdle()

    val resource2 = createResource()
    val executeJob2 = scope1.launch { quinn.execute(resource2) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob1 = scope1.launch { quinn.queueAtBack { resource -> processed.add(resource) } }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    val queueJob2 = scope1.launch { quinn.queueAtBack { resource -> processed.add(resource) } }
    scope1TaskBarrier().awaitAllIdle()

    // Asserting which resource was used effectively asserts which executor processed the task
    assertThat(processed).containsExactly(resource1, resource2).inOrder()
  }

  @Test
  fun multipleExecutes_beforeQueueing_bothSuspend(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob1 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(executeJob1.isActive).isTrue()
    assertThat(executeJob2.isActive).isTrue()
  }

  @Test
  fun multipleExecutes_betweenQueueing_bothSuspend(): Unit = runBlocking {
    val quinn = subject()

    val queueJob1 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(executeJob1.isActive).isTrue()
    assertThat(executeJob2.isActive).isTrue()
  }

  @Test
  fun multipleExecutes_afterQueueing_bothSuspend(): Unit = runBlocking {
    val quinn = subject()

    val queueJob1 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob2 = scope1.launch { quinn.queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

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

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun isExecuting_singleExecutionStopped_isFalse(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isFalse()
  }

  @Test
  fun isExecuting_multipleParallelExecutions_neitherStopped_isTrue(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun isExecuting_multipleParallelExecutions_firstStopped_isTrue(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun isExecuting_multipleParallelExecutions_bothStopped_isFalse(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()
    executeJob2.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isFalse()
  }

  @Test
  fun isExecuting_multipleSequentialExecutions_neitherStopped_isTrue(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun isExecuting_multipleSequentialExecutions_firstStopped_isTrue(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isTrue()
  }

  @Test
  fun isExecuting_multipleSequentialExecutions_bothStopped_isFalse(): Unit = runBlocking {
    val quinn = subject()

    val executeJob1 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob2 = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    executeJob1.cancelAndJoin()
    executeJob2.cancelAndJoin()

    assertThat(quinn.isExecuting.value).isFalse()
  }

  @Test
  fun queueThenExecute_atBack_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = scope1.launch { quinn.queueAtBack { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun queueThenExecute_atFront_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = scope1.launch { quinn.queueAtFront { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun tryQueueThenExecute_atBack_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = scope1.launch { quinn.tryQueueAtBack { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun tryQueueThenExecute_atBack_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val queueJobResult = scope1.async { quinn.tryQueueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJobResult.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun tryQueueThenExecute_atFront_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val queueJob = scope1.launch { quinn.tryQueueAtFront { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun tryQueueThenExecute_atFront_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val queueJobResult = scope1.async { quinn.tryQueueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJobResult.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun executeThenQueue_atBack_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob = scope1.launch { quinn.queueAtBack { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun executeThenQueue_atFront_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob = scope1.launch { quinn.queueAtFront { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun executeThenTryQueue_atBack_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob = scope1.launch { quinn.tryQueueAtBack { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun executeThenTryQueue_atBack_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJobResult = scope1.async { quinn.tryQueueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJobResult.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun executeThenTryQueue_atFront_taskInvoked(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJob = scope1.launch { quinn.tryQueueAtFront { processed.add("first") } }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly("first").inOrder()
  }

  @Test
  fun executeThenTryQueue_atFront_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }
    scope1TaskBarrier().awaitAllIdle()

    val queueJobResult = scope1.async { quinn.tryQueueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJobResult.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun onClose_runningQueuedAtBack_finishes(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    var job1DidRun = false
    val queueJob1 =
        scope2.launch {
          quinn.queueAtBack {
            job1Started.complete(Unit)
            job1Blocker.await()
            job1DidRun = true
          }
        }
    job1Started.await()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(job1DidRun).isTrue()
  }

  @Test
  fun onClose_runningQueuedAtFront_finishes(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    var job1DidRun = false
    val queueJob1 =
        scope2.launch {
          quinn.queueAtFront {
            job1Started.complete(Unit)
            job1Blocker.await()
            job1DidRun = true
          }
        }
    job1Started.await()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(job1DidRun).isTrue()
  }

  @Test
  fun onClose_runningTryQueueAtBack_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    val queueJob1Result =
        scope2.async {
          quinn.tryQueueAtBack {
            job1Started.complete(Unit)
            job1Blocker.await()
          }
        }
    job1Started.await()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob1Result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun onClose_runningTryQueueAtFront_returnsInsertedAndRun(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    val queueJob1Result =
        scope2.async {
          quinn.tryQueueAtFront {
            job1Started.complete(Unit)
            job1Blocker.await()
          }
        }
    job1Started.await()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob1Result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_AND_RUN)
  }

  @Test
  fun onClose_pendingQueueAtBack_isIgnored(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    val queueJob1 =
        scope2.launch {
          quinn.queueAtBack {
            job1Started.complete(Unit)
            job1Blocker.await()
          }
        }
    job1Started.await()

    var job2DidRun = false
    val queueJob2 = scope2.launch { quinn.queueAtBack { job2DidRun = true } }
    scope2TaskBarrier().awaitAllIdle()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(job2DidRun).isFalse()
  }

  @Test
  fun onClose_pendingQueueAtFront_isIgnored(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    val queueJob1 =
        scope2.launch {
          quinn.queueAtBack {
            job1Started.complete(Unit)
            job1Blocker.await()
          }
        }
    job1Started.await()

    var job2DidRun = false
    val queueJob2 = scope2.launch { quinn.queueAtFront { job2DidRun = true } }
    scope2TaskBarrier().awaitAllIdle()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(job2DidRun).isFalse()
  }

  @Test
  fun onClose_pendingTryQueueAtBack_returnsInsertedNotRun(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    val queueJob1 =
        scope2.launch {
          quinn.queueAtBack {
            job1Started.complete(Unit)
            job1Blocker.await()
          }
        }
    job1Started.await()

    val queueJob2Result = scope2.async { quinn.tryQueueAtBack {} }
    scope2TaskBarrier().awaitAllIdle()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob2Result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_NOT_RUN)
  }

  @Test
  fun onClose_pendingTryQueueAtFront_returnsInsertedNotRun(): Unit = runBlocking {
    val quinn = subject()

    val executeJob = scope1.launch { quinn.execute(createResource()) }

    val job1Started = CompletableDeferred<Unit>()
    val job1Blocker = newLatch()
    val queueJob1 =
        scope2.launch {
          quinn.queueAtBack {
            job1Started.complete(Unit)
            job1Blocker.await()
          }
        }
    job1Started.await()

    val queueJob2Result = scope2.async { quinn.tryQueueAtFront {} }
    scope2TaskBarrier().awaitAllIdle()

    val closeJob = scope2.launch { quinn.close() }
    scope2TaskBarrier().awaitAllIdle()

    job1Blocker.countDown()
    scope1TaskBarrier().awaitAllIdle()

    assertThat(queueJob2Result.await()).isEqualTo(Quinn.InsertionResult.INSERTED_NOT_RUN)
  }

  @Test
  fun afterClosure_execute_returnsImmediately(): Unit = runBlocking {
    val quinn = subject()

    quinn.close()

    quinn.execute(createResource())
  }

  @Test
  fun afterClosure_queueAtBack_fails(): Unit = runBlocking {
    val quinn = subject()
    quinn.close()

    val error = assertFailsWith<IllegalStateException> { quinn.queueAtBack {} }

    assertThat(error.message)
        .isEqualTo("This Quinn instance is closed, queueAtBack cannot be used.")
  }

  @Test
  fun afterClosure_queueAtFront_fails(): Unit = runBlocking {
    val quinn = subject()
    quinn.close()

    val error = assertFailsWith<IllegalStateException> { quinn.queueAtFront {} }

    assertThat(error.message)
        .isEqualTo("This Quinn instance is closed, queueAtFront cannot be used.")
  }

  @Test
  fun afterClosure_tryQueueAtBack_returnsRejectedClosed(): Unit = runBlocking {
    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryQueueAtBack {}

    assertThat(tryResult).isEqualTo(Quinn.InsertionResult.REJECTED_CLOSED)
  }

  @Test
  fun afterClosure_tryQueueAtFront_returnsRejectedClosed(): Unit = runBlocking {
    val quinn = subject()
    quinn.close()

    val tryResult = quinn.tryQueueAtFront {}

    assertThat(tryResult).isEqualTo(Quinn.InsertionResult.REJECTED_CLOSED)
  }

  @Test
  fun errorInTask_queuedAtBack_submissionSideErrorHandling_throwsOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.queueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun errorInTask_queuedAtBack_submissionSideErrorHandling_doesNotThrowOnExecution(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.queueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}
        scope1TaskBarrier().awaitAllIdle()

        assertThat(errorReceivedExceptionSide.isCompleted).isFalse()
      }

  @Test
  fun errorInTask_queuedAtBack_submissionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.queueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}

        var ran = false
        quinn.queueAtBack { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_queuedAtBack_executionSideErrorHandling_doesNotThrowOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.queueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        // If here no error was thrown
      }

  @Test
  fun errorInTask_queuedAtBack_executionSideErrorHandling_throwsOnExecution(): Unit = runBlocking {
    val quinn = subject()
    val error = IllegalStateException("Foo")
    val errorReceivedExceptionSide = executeWithCatch(quinn)

    quinn.queueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(errorReceivedExceptionSide.await()).isEqualTo(error)
  }

  @Test
  fun errorInTask_queuedAtBack_executionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.queueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        val newExecutionJob =
            CoroutineScope(scope1Dispatcher()).launch { quinn.execute(createResource()) }

        var ran = false
        quinn.queueAtBack { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_queuedAtFront_submissionSideErrorHandling_throwsOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.queueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun errorInTask_queuedAtFront_submissionSideErrorHandling_doesNotThrowOnExecution(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.queueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}
        scope1TaskBarrier().awaitAllIdle()

        assertThat(errorReceivedExceptionSide.isCompleted).isFalse()
      }

  @Test
  fun errorInTask_queuedAtFront_submissionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.queueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}

        var ran = false
        quinn.queueAtFront { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_queuedAtFront_executionSideErrorHandling_doesNotThrowOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.queueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        // If here no error was thrown
      }

  @Test
  fun errorInTask_queuedAtFront_executionSideErrorHandling_throwsOnExecution(): Unit = runBlocking {
    val quinn = subject()
    val error = IllegalStateException("Foo")
    val errorReceivedExceptionSide = executeWithCatch(quinn)

    quinn.queueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(errorReceivedExceptionSide.await()).isEqualTo(error)
  }

  @Test
  fun errorInTask_queuedAtFront_executionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.queueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        val newExecutionJob =
            CoroutineScope(scope1Dispatcher()).launch { quinn.execute(createResource()) }

        var ran = false
        quinn.queueAtFront { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_tryQueuedAtBack_submissionSideErrorHandling_throwsOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun errorInTask_tryQueuedAtBack_submissionSideErrorHandling_doesNotThrowOnExecution(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}
        scope1TaskBarrier().awaitAllIdle()

        assertThat(errorReceivedExceptionSide.isCompleted).isFalse()
      }

  @Test
  fun errorInTask_tryQueuedAtBack_submissionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}

        var ran = false
        quinn.tryQueueAtBack { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_tryQueuedAtBack_executionSideErrorHandling_doesNotThrowOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        // If here no error was thrown
      }

  @Test
  fun errorInTask_tryQueuedAtBack_executionSideErrorHandling_throwsOnExecution(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(errorReceivedExceptionSide.await()).isEqualTo(error)
      }

  @Test
  fun errorInTask_tryQueuedAtBack_executionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.tryQueueAtBack(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        val newExecutionJob =
            CoroutineScope(scope1Dispatcher()).launch { quinn.execute(createResource()) }

        var ran = false
        quinn.tryQueueAtBack { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_tryQueuedAtFront_submissionSideErrorHandling_throwsOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        val submissionSideError =
            assertFailsWith<IllegalStateException> {
              quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
            }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(submissionSideError).isEqualTo(error)
      }

  @Test
  fun errorInTask_tryQueuedAtFront_submissionSideErrorHandling_doesNotThrowOnExecution(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}
        scope1TaskBarrier().awaitAllIdle()

        assertThat(errorReceivedExceptionSide.isCompleted).isFalse()
      }

  @Test
  fun errorInTask_tryQueuedAtFront_submissionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        try {
          quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) { throw error }
        } catch (e: IllegalStateException) {}

        var ran = false
        quinn.tryQueueAtFront { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  @Test
  fun errorInTask_tryQueuedAtFront_executionSideErrorHandling_doesNotThrowOnSubmission(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        // If here no error was thrown
      }

  @Test
  fun errorInTask_tryQueuedAtFront_executionSideErrorHandling_throwsOnExecution(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(errorReceivedExceptionSide.await()).isEqualTo(error)
      }

  @Test
  fun errorInTask_tryQueuedAtFront_executionSideErrorHandling_quinnRemainsOperational(): Unit =
      runBlocking {
        val quinn = subject()
        val error = IllegalStateException("Foo")
        val errorReceivedExceptionSide = executeWithCatch(quinn)

        quinn.tryQueueAtFront(ErrorHandling.DELIVER_TO_EXECUTION_SIDE) { throw error }
        scope1TaskBarrier().awaitAllIdle()

        val newExecutionJob =
            CoroutineScope(scope1Dispatcher()).launch { quinn.execute(createResource()) }

        var ran = false
        quinn.tryQueueAtFront { ran = true }
        scope1TaskBarrier().awaitAllIdle()

        assertThat(ran).isTrue()
      }

  /** Returns the subject under test. Must return the same instance each time. */
  abstract fun subject(): Quinn<T>

  /** Returns a dispatcher for use in tests. Must be distinct from [scope2Dispatcher]. */
  abstract fun scope1Dispatcher(): CoroutineDispatcher

  /**
   * Returns a task barrier that gates [scope1Dispatcher]. Must be distinct from
   * [scope2TaskBarrier].
   */
  abstract fun scope1TaskBarrier(): TestingTaskBarrier

  /** Returns a dispatcher for use in tests. Must be distinct from [scope1Dispatcher]. */
  abstract fun scope2Dispatcher(): CoroutineDispatcher

  /**
   * Returns a task barrier that gates [scope2Dispatcher]. Must be distinct from
   * [scope1TaskBarrier].
   */
  abstract fun scope2TaskBarrier(): TestingTaskBarrier

  /**
   * Creates a new resource that can be supplied to [Quinn]. A new value must be returned on each
   * call (i.e. not equals-identical to any of the previous instances).
   */
  abstract fun createResource(): T

  /**
   * Launches a job to run `execute` on [quinn] and returns a [Deferred] that completes with the
   * error thrown by `execute`, or `null` if `execute` returns without error.
   */
  private suspend fun executeWithCatch(quinn: Quinn<T>): Deferred<Throwable?> {
    val exception = CompletableDeferred<Throwable?>()
    val exceptionCatcher = CoroutineExceptionHandler { _, e -> exception.complete(e) }
    scope1.launch(exceptionCatcher) {
      quinn.execute(createResource())
      exception.complete(null)
    }
    scope1TaskBarrier().awaitAllIdle()
    return exception
  }

  /** Provides a new [CoundDownLatch] with a count of `1`. The latch is registered in [latches]. */
  private fun newLatch(): CountDownLatch = CountDownLatch(1).also { latches.add(it) }

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
