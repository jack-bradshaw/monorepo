package io.matthewbradshaw.merovingian.model

import io.matthewbradshaw.merovingian.lifecycle.Pausable
import io.matthewbradshaw.merovingian.lifecycle.Preparable
import kotlinx.coroutines.flow.Flow

/**
 * A game designed to be run on the [jMonkey 3](https://jmonkeyengine.org/) game engine.
 */
interface Game {

  suspend fun prepare()

  /**
   * The physical representation of the game world, as a flow which emits a new value when the root item is replaced.
   */
  fun representation(): Flow<GameItem>

  suspend fun logic() = Unit
}