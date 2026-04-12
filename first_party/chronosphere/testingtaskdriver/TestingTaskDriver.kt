package com.jackbradshaw.chronosphere.testingtaskdriver

import com.jackbradshaw.chronosphere.advancable.Advancable

/**
 * Drives a collection of [Advancable] systems forward in virtual time.
 *
 * Example usage:
 * ```
 * class FooTest {
 *   @Inject lateinit var taskDriver: TestingTaskDriver
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
 *     foo.startComplexBackgroundWork()
 *     taskDriver.advanceAllBy(100)
 *
 *     assertThat(foo.isComplete).isTrue()
 *   }
 * }
 * ```
 *
 * The interface does not restrict or specify which systems are driven or how they are specified,
 * with both static constructor-time systems and dynamic registry-based systems permitted (amongst
 * others). What matters is consistency: If the implementation claims to drive a system then
 * `advanceAllBy` must block until that system has been driven forward; however, much like
 * [Advancable], the task driver does not guarantee that any system remains at a fixed point in time
 * after [awaitAllIdle] returns, as other systems could always drive the system forward.
 * Furthermore, the interface makes no guarantees about the order of advancement, such that
 * implementations are free to drive systems sequentially, concurrently, or in any other way that
 * ensures all have advanced by the required duration before `advanceAllBy` returns.
 *
 * [TestingTaskDriver] is itself [Advancable] for ease of composition.
 */
interface TestingTaskDriver : Advancable {

  /**
   * Advances all driven [Advancable] virtual clocks forward by [millis] milliseconds.
   *
   * All driven [Advancable] instances are guaranteed to be advanced before the function returns;
   * however, there is no guarantee they will not advance separately, as unrelated systems can
   * always advance the system in the background (e.g. hardware interrupts, ungated executors, etc).
   */
  fun advanceAllBy(millis: Int)

  /** Produces [TestingTaskDriver] instances. */
  interface Factory {
    /** Creates a new [TestingTaskDriver] that operates on [driving]. */
    fun create(driving: Set<Advancable>): TestingTaskDriver
  }
}
