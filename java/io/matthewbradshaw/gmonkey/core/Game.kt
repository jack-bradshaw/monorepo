package io.matthewbradshaw.gmonkey.core

/**
 * A game designed to be run on the [jMonkey 3](https://jmonkeyengine.org/) game engine.
 */
interface Game : Preparable, Pausable {
  fun ui(): Flow<Set<Space>>
  suspend fun logic()
}