package io.jackbradshaw.otter.input

import kotlinx.coroutines.flow.Flow

interface Keyboard {

  fun buttonEvents(): Flow<KeyPress>

  data class ButtonEvent(
      val key: KeyInput,
      val pressed: Boolean
  )
}