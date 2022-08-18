package io.jackbradshaw.jockstrap.structure.frames

import io.jackbradshaw.klu.flow.MutableFlower
import io.jackbradshaw.jockstrap.physics.Placement

interface Placeable {
  val placement: MutableFlower<Placement>
}