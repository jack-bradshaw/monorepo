package io.matthewbradshaw.jockstrap.model.frames

interface Hostable {
  suspend fun onAttached() = Unit
  suspend fun onDetached() = Unit
}
