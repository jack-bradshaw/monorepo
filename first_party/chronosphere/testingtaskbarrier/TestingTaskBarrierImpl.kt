package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.jackbradshaw.chronosphere.idleable.Idleable
import javax.inject.Inject

/**
 * The default implementation of [TestingTaskBarrier].
 *
 * Idle state is determined by checking [gating] in a loop until the idle state is reached, which
 * occurs when all Idleables in [gating] report idle [sweepFactor] consecutive times in a row. For
 * example, with a sweep factor of 3 and two gated systems, the barrier checks system 1, system 2,
 * system 1, system 2, system 1, system 2, and only reports idle if every check registered as idle.
 *
 * A default sweep factor of 10x the number of gated systems was chosen as an overestimate for
 * safety.
 */
class TestingTaskBarrierImpl
internal constructor(
    private val gating: Set<Idleable>,
    private val sweepFactor: Int = 10 * gating.size,
) : TestingTaskBarrier {

  override fun awaitAllIdle() {
    while (true) {
      if (isIdle()) return
    }
  }

  override fun isIdle(): Boolean {
    repeat(sweepFactor) { if (!sweep()) return false }
    return true
  }

  /**
   * Performs a single check of each gated system and returns whether all are idle. Exits early if a
   * non-idle system is found.
   */
  private fun sweep(): Boolean {
    for (system in gating) {
      if (!system.isIdle()) return false
    }
    return true
  }

  /** Factory that returns [TestingTaskBarrierImpl] instances. */
  class Factory @Inject internal constructor() : TestingTaskBarrier.Factory {
    override fun create(gating: Set<Idleable>): TestingTaskBarrier {
      return TestingTaskBarrierImpl(gating.toSet())
    }
  }
}
