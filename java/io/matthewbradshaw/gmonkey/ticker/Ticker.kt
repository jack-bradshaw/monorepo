package io.matthewbradshaw.gmonkey.ticker

import kotlinx.coroutines.flow.Flow

/**
 * Provides flow-based access to game time.
 */
interface Ticker {
  /**
   * Gets the net time since the game started, measured in seconds.
   */
  fun netTimeSec(): TimeSec

  /**
   * Gets a flow which emits a value each time the game triggers an update. The value holds the time since the previous
   * pulse (otherwise known as time per frame or TPF), measured in seconds.
   */
  fun pulse(): Flow<TimeSec>

  /**
   * Notifies the ticker as update has occurred. The [timeSinceLastTickSec] value holds the time since the previous
   * update (otherwise known as time per frame or TPF), measured in seconds.
   */
  suspend fun tick(timeSinceLastTickSec: TimeSec)
}

typealias TimeSec = Float