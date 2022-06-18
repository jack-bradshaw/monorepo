package io.matthewbradshaw.merovingian.clock

import kotlinx.coroutines.flow.Flow

/**
 * Provides flow-based access to the current time with reference to the game engine.
 */
interface Clock {
  /**
   * Gets the time since the game engine started as a flow, where each value holds the time since the previous emission
   * (measured in seconds). If the total time is needed instead, use [totalTimeSec].
   */
  fun deltaSec(): Flow<Double>

  /**
   * Gets the time since the game engine started as a flow. If the delta is needed instead, use [deltaTimeSec]
   */
  fun totalSec(): Flow<Double>
}