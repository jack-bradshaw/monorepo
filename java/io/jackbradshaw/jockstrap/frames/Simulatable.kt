package io.jackbradshaw.jockstrap.frames

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow

interface Simulatable {

  /**
   * Applies simulation effects indefinitely.
   */
  suspend fun simulate() = Unit

  /**
   * Removes simulation effects indefinitely.
   */
  suspend fun desimulate() = Unit

  /**
   * Returns a flow which emits whether the simulation is currently in a steady state.
   *
   * A steady state is one which will undergo no further simulation until disturbed. Examples include a pendulum hanging
   * straight downwards with no momentum, a box which has landed on solid ground, and a player with no current
   * controller inputs. Small amounts of noise at steady state are tolerable and should not result in false being
   * emitted.
   */
  fun isSteadyState(): Flow<Boolean> = flowOf(true)
}