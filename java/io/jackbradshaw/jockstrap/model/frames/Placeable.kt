package io.jackbradshaw.jockstrap.model.frames

import io.jackbradshaw.jockstrap.physics.Placement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import io.jackbradshaw.klu.flow.Flower

interface Placeable {
  val placement: Flower<Placement>
}