package io.jackbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold

suspend fun <K, V> Flow<Pair<K, V>>.toMap() = fold(mutableMapOf<K, V>()) {
  accumulator, element -> accumulator.also { it[element.first] = element.second }
}