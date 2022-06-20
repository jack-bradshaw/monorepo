package io.matthewbradshaw.gmonkey.model

import io.matthewbradshaw.gmonkey.ui.Level

/**
 * A game designed to be run on the [jMonkey 3](https://jmonkeyengine.org/) game engine.
 */
interface Game<S: MessageLite> : Restorable<S>, Preparable, Pausable {
  fun world(): World<*>
  fun player(): Player
}