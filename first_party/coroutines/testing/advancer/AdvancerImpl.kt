package com.jackbradshaw.coroutines.testing.advancer

import javax.inject.Inject
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent

/**
 * Implementation of [Advancer] that operates on [testScope].
 * 
 * Advancing until idle means advancing until [testScope] has no more work queued, and includes any
 * work queued during the advancement. Advancing through tick means advancing until the virtual
 * clock associated with [testScope] moves to the next tick, and includes any work queued for the
 * present moment during advancement (e.g. launched without delay).
 */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class AdvancerImpl @Inject constructor(private val testScope: TestScope) : Advancer {

  override suspend fun advanceUntilIdle() {
    testScope.testScheduler.advanceUntilIdle()
  }

  override suspend fun advanceThroughTick() {
    testScope.runCurrent()
  }
}
