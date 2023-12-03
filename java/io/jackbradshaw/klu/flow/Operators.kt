package io.jackbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold

/**
 * Collects a [Flow] of [Pair]s into an immutable map containing the values.
 *
 * Suspends until the upstream flow terminates so all values can be collected. If the flow contains
 * duplicate keys, the latest value is retained. For example, a Flow of (key=1, value=2) then
 * (key=1, value=3) will result in a map containing (key=1, value=3).
 */
suspend fun <K, V> Flow<Pair<K, V>>.collectToMap(): Map<K, V> =
    fold(mutableMapOf<K, V>()) { accumulator, element ->
      accumulator.also { it[element.first] = element.second }
    }
