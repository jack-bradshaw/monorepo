package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.pulsar.Pulsar

/**
 * A controllable pulsar for use in tests.
 *
 * Each time [emit] is called, the pulsar emits one pulse to each existing flow. Pulses are not
 * emitted continuously as they are in the production implementation, and calling [emit] is the only
 * way to trigger a pulse.
 *
 * Using dependency injection in tests to substitute a production `Pulsar` with a `TestPulsar`
 * allows granular control over loops that would otherwise block the test forever and prevent an
 * idle state from being reached.
 *
 * Example usage showing a test of a service that routinely polls for new data on a background
 * thread:
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
 *   assertThat(pollingService.latestData()).isEqualTo("hello world"")
 * }
 * ```
 */
interface TestPulsar : Pulsar {
  /**
   * Sends a pulse to all existing [pulse] flows, and suspends until the emission has occurred (but
   * does not wait for pulse flows to process the pulse).
   */
  suspend fun emit()
}
