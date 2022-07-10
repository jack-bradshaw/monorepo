package io.matthewbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow

typealias BinaryDeltaFlow<T> = Flow<Pair<T, BinaryDelta>>

suspend fun <T> BinaryDeltaFlow<T>.toSetFlow(): Flow<Set<T>> = TODO()

enum class BinaryDelta {
  INCLUDE,
  EXCLUDE
}