package io.matthewbradshaw.jockstrap.model.frames

interface Simulatable {
  suspend fun preSimulate() = Unit
  suspend fun startSimulate() = Unit
  suspend fun pauseSimulation() = Unit
}