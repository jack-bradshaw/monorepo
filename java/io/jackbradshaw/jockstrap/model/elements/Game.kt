package io.jackbradshaw.jockstrap.elements

import kotlinx.coroutines.flow.Flow

/**
 * A video game consisting of one or more levels.
 */
interface Game {
  suspend fun start()
  suspend fun stop()
  fun level(): Flow<Level?>
}

