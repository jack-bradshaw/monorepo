package com.jackbradshaw.concurrency.quinn.testing.idleable

import java.util.concurrent.CountDownLatch
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.ErrorBehaviour
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.After
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

abstract class IdleableQuinnTest {

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
    runBlocking {
      subjectLinkedScopeHandle.cancelAndJoin()
      subjectIndependentScopeHandle.cancelAndJoin()
      subject().close()
    }
  }

  abstract fun subject(): IdleableQuinn<String>
  abstract fun subjectLinkedDispatcher(): CoroutineDispatcher
  abstract fun subjectLinkedTaskBarrier(): TestingTaskBarrier
  
  abstract fun subjectIndependentDispatcher(): CoroutineDispatcher
  abstract fun subjectIndependentTaskBarrier(): TestingTaskBarrier

  @Test
  fun isIdle_noTasksSubmitted_returnsTrue() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecutingQueueAtBack_returnsFalse() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val executionLock = CountDownLatch(1)
    val executionStarted = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        executionStarted.complete(Unit)
        executionLock.await()
      }
    }

    executionStarted.await()
    assertThat(subject().isIdle()).isFalse()

    executionLock.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()
    subjectIndependentTaskBarrier().awaitAllIdle()
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecutingTryQueueAtBack_returnsFalse() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val executionLock = CountDownLatch(1)
    val executionStarted = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().tryQueueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        executionStarted.complete(Unit)
        executionLock.await()
      }
    }

    executionStarted.await()
    assertThat(subject().isIdle()).isFalse()

    executionLock.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()
    subjectIndependentTaskBarrier().awaitAllIdle()
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecutingQueueAtFront_returnsFalse() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val executionLock = CountDownLatch(1)
    val executionStarted = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtFront(ErrorBehaviour.DELIVER_TO_CALLER) {
        executionStarted.complete(Unit)
        executionLock.await()
      }
    }

    executionStarted.await()
    assertThat(subject().isIdle()).isFalse()

    executionLock.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()
    subjectIndependentTaskBarrier().awaitAllIdle()
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecutingTryQueueAtFront_returnsFalse() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val executionLock = CountDownLatch(1)
    val executionStarted = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().tryQueueAtFront(ErrorBehaviour.DELIVER_TO_CALLER) {
        executionStarted.complete(Unit)
        executionLock.await()
      }
    }

    executionStarted.await()
    assertThat(subject().isIdle()).isFalse()

    executionLock.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()
    subjectIndependentTaskBarrier().awaitAllIdle()
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_withMultipleQueued_noneFinish_returnsFalse() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val lock1 = CountDownLatch(1)
    val started1 = CompletableDeferred<Unit>()
    val lock2 = CountDownLatch(1)
    val started2 = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        started1.complete(Unit)
        lock1.await()
      }
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        started2.complete(Unit)
        lock2.await()
      }
    }

    started1.await()
    assertThat(subject().isIdle()).isFalse()
    
    lock1.countDown()
    lock2.countDown()
  }

  @Test
  fun isIdle_withMultipleQueued_oneFinishOneRunning_returnsFalse() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val lock1 = CountDownLatch(1)
    val started1 = CompletableDeferred<Unit>()
    val lock2 = CountDownLatch(1)
    val started2 = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        started1.complete(Unit)
        lock1.await()
      }
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        started2.complete(Unit)
        lock2.await()
      }
    }

    started1.await()
    lock1.countDown()
    started2.await()
    
    assertThat(subject().isIdle()).isFalse()
    
    lock2.countDown()
  }

  @Test
  fun isIdle_withMultipleQueued_allFinish_returnsTrue() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val lock1 = CountDownLatch(1)
    val started1 = CompletableDeferred<Unit>()
    val lock2 = CountDownLatch(1)
    val started2 = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        started1.complete(Unit)
        lock1.await()
      }
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        started2.complete(Unit)
        lock2.await()
      }
    }

    started1.await()
    lock1.countDown()
    started2.await()
    lock2.countDown()
    subjectLinkedTaskBarrier().awaitAllIdle()
    subjectIndependentTaskBarrier().awaitAllIdle()
    
    assertThat(subject().isIdle()).isTrue()
  }

  

  @Test
  fun isIdle_whileClosingAndFinishingLastTask_isNotIdle() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val executionLock = CountDownLatch(1)
    val executionStarted = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        executionStarted.complete(Unit)
        executionLock.await()
      }
    }

    executionStarted.await()
    val closeJob = subjectLinkedScope.launch { subject().close() }

    // wait briefly for close to acquire the lock and get stuck
    delay(50)
    assertThat(subject().isIdle()).isFalse()
    
    executionLock.countDown()
    closeJob.join()
  }

  @Test
  fun isIdle_afterClosingAndFinishingLastTask_isIdle() = runBlocking {
    subjectLinkedScope.launch { subject().execute("test-resource") }
    val executionLock = CountDownLatch(1)
    val executionStarted = CompletableDeferred<Unit>()

    subjectIndependentScope.launch {
      subject().queueAtBack(ErrorBehaviour.DELIVER_TO_CALLER) {
        executionStarted.complete(Unit)
        executionLock.await()
      }
    }

    executionStarted.await()
    val closeJob = subjectLinkedScope.launch { subject().close() }

    // wait briefly for close to acquire the lock and get stuck
    delay(50)

    executionLock.countDown()
    closeJob.join()
    subjectLinkedTaskBarrier().awaitAllIdle()
    subjectIndependentTaskBarrier().awaitAllIdle()
    assertThat(subject().isIdle()).isTrue()
  }
}