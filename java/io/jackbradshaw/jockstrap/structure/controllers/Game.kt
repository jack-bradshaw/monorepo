package io.jackbradshaw.jockstrap.structure.controllers

import kotlinx.coroutines.flow.Flow

/**
 * A video game consisting of one or more levels.
 */
interface Game {
  suspend fun start()
  suspend fun stop()
  val level: Flow<Level?>
}

