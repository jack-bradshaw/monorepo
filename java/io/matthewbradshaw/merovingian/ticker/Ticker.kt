package io.matthewbradshaw.merovingian.ticker

import kotlinx.coroutines.flow.Flow

/**
 * Provides flow-based access to game time.
 */
interface Ticker {
  /**
   * Gets the total time since the game engine started, measured in seconds.
   */
  fun totalTimeS(): TimeSec

  /**
   * Gets a flow which emits a value each time the game engine triggers an update. The emitted value holds the time
   * since the previous pulse (otherwise known as time per frame or TPF), measured in seconds.
   */
  fun pulseDeltaS(): Flow<TimeSec>

  /**
   * Gets a flow which emits a value each time the game triggers an update. The emitted value holds the total time since
   * the game engine started, measured in seconds.
   */
  fun pulseTotalS(): Flow<TimeSec>

  /**
   * Notifies the ticker as update has occurred. The [timeSinceLastTickSec] value holds the time since the previous
   * update (otherwise known as time per frame or TPF), measured in seconds.
   */
  suspend fun tick(timeSinceLastTickS: TimeSec)
}

typealias TimeSec = Float