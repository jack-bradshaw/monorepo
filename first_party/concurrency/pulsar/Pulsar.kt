package com.jackbradshaw.concurrency.pulsar

import kotlinx.coroutines.flow.Flow

/**
 * Emits pulses indefinitely.
 *
 * Pulsar allows kotlin flows to perform repeated tasks over time. For example:
 * ```
 * class DataSource(pulsar: Pulsar) {
 *   fun dataFlow(delayDuration: Duration) = pulsar
 *       .pulses()
 *       .map {
 *         delay(delayDuration);
 *         dataSource.fetchData()
 *       }
 * }
 * ```
 *
 * The above code emits the latest data repeatedly and indefinitely.
 */
interface Pulsar {
  /** Creates a new cold flow which emits pulses indefinitely. */
  fun pulses(): Flow<Unit>
}
