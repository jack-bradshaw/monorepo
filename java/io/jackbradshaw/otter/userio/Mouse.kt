package io.jackbradshaw.otter.userio

import kotlinx.coroutines.flow.Flow

interface Mouse {
  fun buttonEvents(): Flow<ButtonEvent>
  fun scrollWheelEvents(): Flow<ScrollWheelEvent>
  fun dragEvent(): Flow<DragEvent>

  data class ButtonEvent(
      val button: MouseInput,
      val pressed: Boolean
  )

  data class ScrollWheelEvent(
      val direction: ScrollDirection,

  )

  enum class ScrollDirection {
    UP,
    DOWN
  }

  enum class DragDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }
}