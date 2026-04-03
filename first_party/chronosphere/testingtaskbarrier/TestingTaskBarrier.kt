package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.jackbradshaw.chronosphere.idleable.Idleable

/**
 * Drives all registered asynchronous systems to idle.
 *
 * In pure computer science theory, a task barrier is guaranteed to resume execution if and only if
 * all gated systems have reached a terminal idle state. Because real-world systems are heavily
 * coupled to external schedulers, the network, and the OS, true systemic idling is effectively
 * impossible. Therefore, in practice, it is necesssary to gate the barrier exclusively on the
 * executors, dispatchers, and loops that directly affect the system under test, assuming all
 * un-gated components are irrelevant, by virtue of being unused in the broader runtime program,
 * by virtue of them being antipatterns.
 */
interface TestingTaskBarrier : Idleable {

  /** Blocks the calling thread until all gated [Idleable] instances return true. */
  fun awaitAllIdle()

  /** Produces [TestingTaskBarrier] instances. */
  interface Factory {
    /**
     * Creates a new [TestingTaskBarrier] that operates on [gating]. The produced barrier will only
     * report idle when every [Idleable] in [gating] is idle.
     */
    fun create(gating: Set<Idleable>): TestingTaskBarrier
  }
}
