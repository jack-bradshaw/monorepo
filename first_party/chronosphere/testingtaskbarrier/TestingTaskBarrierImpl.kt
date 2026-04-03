package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.jackbradshaw.chronosphere.idleable.Idleable
import javax.inject.Inject

/**
 * A [TestingTaskBarrier] that gates [gating].
 * 
 * It will only unblock [awaitAllIdle] if all values in
 * [gating] report idle after [sweepFactor] consecutive checks (i.e. if sweep factor is 3 and there
 * are two values to gate, the system will check system 1, system 2, system 1, system 2, system 1,
 * system 2, and only report idle if the sweep completes with all idle each time).
 */
class TestingTaskBarrierImpl
internal constructor(
    private val gating: Set<@JvmSuppressWildcards Idleable>,
    private val sweepFactor: Int = 10 * gating.size,
) : TestingTaskBarrier {
  
  override fun awaitAllIdle() {
    while (true) {
      if (isIdle()) return
    }
  }

  override fun isIdle(): Boolean {
    repeat(sweepFactor) {
      if (!checkIdleablesOnce()) return false
    }
    return true
  }

  private fun checkIdleablesOnce(): Boolean {
    for (system in gating) {
      if (!system.isIdle()) return false
    }
    return true
  }

  class Factory @Inject internal constructor() : TestingTaskBarrier.Factory {
    override fun create(gating: Set<Idleable>): TestingTaskBarrier {
      // Defensive set copy to avoid external modifications after creation.
      return TestingTaskBarrierImpl(gating.toSet())
    }
  }
}
