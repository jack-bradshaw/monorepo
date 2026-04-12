package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.jackbradshaw.chronosphere.idleable.Idleable

/**
 * Waits for a collection of [Idleable] systems to all reach an idle state so that tests may block
 * until all background work is complete.
 *
 * Example usage:
 * ```
 * class FooTest {
 *   @Inject lateinit var taskBarrier: TestingTaskBarrier
 *   @Inject lateinit var foo: Foo
 *
 *   @Before
 *   fun setup() {
 *     // Details omitted for example purposes.
 *     inject(this)
 *   }
 *
 *   @Test
 *   fun test() {
 *     val foo = Foo()
 *
 *     foo.startComplexBackgroundWork()
 *     taskBarrier.awaitAllIdle()
 *
 *     assertThat(foo.isComplete).isTrue()
 *   }
 * }
 * ```
 *
 * The interface does not restrict or specify which systems are guarded or how they are specified,
 * with both static constructor-time systems and dynamic registry-based systems permitted (amongst
 * others). What matters is consistency: If the implementation claims to gate a system then
 * `awaitAllIdle` must block until that system is idle; however, much like [Idleable], the task
 * barrier does not guarantee that any system remains idle after [awaitAllIdle] returns, as other
 * systems could always wake the system and resume work.
 *
 * [TestingTaskBarrier] is itself [Idleable] for ease of composition.
 */
interface TestingTaskBarrier : Idleable {

  /**
   * Blocks the calling thread until all gated Idleable systems are idle.
   *
   * The calling thread is guaranteed to be unblocked when all gated systems are idle; however,
   * there is no guarantee they will remain idle after this, as unrelated and ungated systems can
   * always move the system out of idle (e.g. hardware interrupts, ungated executors, etc).
   */
  fun awaitAllIdle()

  /** Produces [TestingTaskBarrier] instances. */
  interface Factory {
    /** Creates a new [TestingTaskBarrier] that operates on [gating]. */
    fun create(gating: Set<Idleable>): TestingTaskBarrier
  }
}
