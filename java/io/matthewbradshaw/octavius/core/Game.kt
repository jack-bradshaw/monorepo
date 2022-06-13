package io.matthewbradshaw.octavius.core

import io.matthewbradshaw.octavius.ui.Frameable
import kotlinx.coroutines.flow.Flow
import io.matthewbradshaw.octavius.heartbeat.Ticker

interface Game {
  fun ui(): Flow<Frameable>
  fun paradigm(): Paradigm
}