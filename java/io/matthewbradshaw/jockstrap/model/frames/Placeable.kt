package io.matthewbradshaw.jockstrap.model.frames

import io.matthewbradshaw.jockstrap.physics.Placement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import io.matthewbradshaw.klu.flow.Flower

interface Placeable {
  val placement: Flower<Placement>
}