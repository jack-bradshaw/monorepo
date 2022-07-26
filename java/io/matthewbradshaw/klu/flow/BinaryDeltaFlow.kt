package io.matthewbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow

typealias BinaryDeltaPair<T> = Pair<T, BinaryDelta>
typealias BinaryDeltaFlow<T> = Flow<BinaryDeltaPair<T>>

enum class BinaryDelta {
  INCLUDE,
  EXCLUDE
}