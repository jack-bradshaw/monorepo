package io.jackbradshaw.jockstrap.model.frames

interface Hostable {
  suspend fun onAttached() = Unit
  suspend fun onDetached() = Unit
}
