package com.jackbradshaw.coroutines.testing.advancer

/**
 * Advances through scheduled work in a test and suspends until work completes.
 *
 * The specific resource being advanced (e.g. test scheduler, coroutine scope etc.) is an
 * implementation detail which must be documented by the implementation.
 */
interface Advancer {

  /**
   * Advances until there are no pending tasks remaining in the work queue.
   *
   * Executes all scheduled coroutines and their recursive schedules until no further work remains.
   * Blocks until the scheduler reaches an idle state. This function will suspend indefinitely in
   * tests which exercise code containing infinite loops.
   */
  suspend fun advanceUntilIdle()

  /**
   * Advances the test scheduler through a single tick of current work.
   *
   * Executes tasks that are ready to run immediately at the current virtual time. Includes
   * recursively scheduled immediate tasks but does not advance virtual time.
   */
  suspend fun advanceThroughTick()
}
