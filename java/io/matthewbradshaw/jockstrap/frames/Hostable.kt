package io.matthewbradshaw.jockstrap.frames

interface Hostable {
  suspend fun onAttached() = Unit
  suspend fun onDetached() = Unit
}
