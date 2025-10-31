package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.pulsar.Pulsar

/**
 * A controllable pulsar for use in tests.
 *
 * Each time [emit] is called, the pulsar emits one pulse to each existing flow. Pulses are not
 * emitted continuously, as they would be in the production implementation, and calling [emit] is
 * the only way to trigger a pulse. When combined with dependency injection, this allows infinite
 * and long-running loops to be used without blocking tests. For example, consider a test that
 * fetches data on a background thread repeatedly:
 * ```
 * @Inject lateinit var testPulsar: TestPulsar
 * @Inject lateinit var testScope: TestScope
 *
 * @Test
 * fun someTest() = runBlocking {
 *   val foo = "hello world"
 *   val pollingService = FooPollingService(testPulsar)
 *
 *   testPulsar.emit()
 *   testScope.advanceUntilIdle()
 *
 *   assertThat(pollingService.latestData()).isEqualTo("hello world")
 * }
 * ```
 *
 * With a `while(true)` loop, the `advanceUntilIdle` call would never complete and the test would
 * run indefinitely, but with a pulsar used internally, the loop can be controlled in tests and it
 * only the desired cycles will be run.
 */
interface TestPulsar : Pulsar {
  /**
   * Sends a pulse to all existing [pulse] flows, suspending until the pulse has been delivered to
   * all flows.
   */
  suspend fun emit()
}
