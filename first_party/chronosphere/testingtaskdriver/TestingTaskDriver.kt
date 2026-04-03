package com.jackbradshaw.chronosphere.testingtaskdriver

import com.jackbradshaw.chronosphere.advancable.Advancable

/**
 * A utility for manually stepping the virtual clocks of integrated systems forward in time.
 *
 * In pure computer science theory, a task driver is guaranteed to advance time perfectly uniformly
 * if and only if all systems capable of temporal evaluation are slaved to the driver's clock.
 * Because real-world environments depend on external oscillators and the physics of the host
 * machine, true deterministic temporal advancement is effectively impossible. Therefore, in
 * practice, it is sufficient to gate the driver exclusively on the virtual clocks that directly
 * affect the system under test, assuming all others are irrelevant.
 *
 * This interface explicitly avoids declaring suspending functions because it inherently operates on
 * concurrent systems and represents a blocking progression; requiring it to be used from a
 * coroutine context would defeat the purpose of explicitly manipulating time.
 */
interface TestingTaskDriver {
  /** Advances all gated [Advancable] virtual clocks by the specified number of milliseconds. */
  fun advanceAllBy(millis: Int)

  /** Produces [TestingTaskDriver] instances. */
  interface Factory {
    /**
     * Creates a new [TestingTaskDriver] that operates on [gating]. The produced driver will advance
     * every [Advancable] in [gating] when [advanceAllBy] is invoked.
     */
    fun create(gating: Set<Advancable>): TestingTaskDriver
  }
}
