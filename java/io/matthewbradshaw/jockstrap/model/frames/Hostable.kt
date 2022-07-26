package io.matthewbradshaw.jockstrap.model.frames

interface Hostable {
  suspend fun onAttached()
  suspend fun onDetached()

}
