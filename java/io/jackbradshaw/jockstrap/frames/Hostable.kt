package io.jackbradshaw.jockstrap.frames

interface Hostable {
  suspend fun onAttached() = Unit
  suspend fun onDetached() = Unit
}
