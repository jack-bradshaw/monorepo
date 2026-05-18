package com.jackbradshaw.closet.suspending

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/** Abstract test that all [SuspendableClosable] instances should pass. */
abstract class SuspendableClosableTest<T : SuspendableClosable> {

  private val testScopeHandle = Job()

  private val testScope by lazy { CoroutineScope(testDispatcher() + testScopeHandle) }

  @After
  open fun tearDown() {
    runBlocking {
      testScopeHandle.cancelAndJoin()
      subject().close()
    }
  }

  @Test
  fun close_isIdempotent() = runBlocking {
    val closable = subject()

    closable.close()
    closable.close()
    closable.close()

    // If here, no exception was thrown, test passed
  }

  @Test
  fun close_isParallelizable() = runBlocking {
    // Two layered parallelization to increase the chance of concurrent execution.
    val jobs = List(10) { testScope.launch { repeat(100) { subject().close() } } }
    jobs.forEach { it.join() }
  }

  /* Gets the subject under test. The same instance must be returned on each call. */
  abstract fun subject(): T

  abstract fun testDispatcher(): CoroutineDispatcher
}
