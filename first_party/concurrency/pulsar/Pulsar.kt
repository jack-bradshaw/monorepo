package com.jackbradshaw.concurrency.pulsar

import kotlinx.coroutines.flow.Flow

/**
 * Emits pulses indefinitely.
 *
 * Example usage:
 * ```
 * val pulsar = TODO() // inject etc
 * val dataSource = TODO() // inject etc
 * val dataFlow = pulsar
 *     .pulses()
 *     .map { dataSource.fetchData() }
 *     .collect { println(it) }
 *
 * val slowerDataFlow = pulsar
 *     .pulses()
 *     .map {
 *       delay(1000);
 *       dataSource.fetchData()
 *     }
 *     .collect { println(it) }
 * ```
 */
interface Pulsar {
  /** Creates a new cold flow which emits pulses indefinitely. */
  fun pulses(): Flow<Unit>
}
