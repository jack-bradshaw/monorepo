package io.jackbradshaw.otter.structure.frames

import io.jackbradshaw.klu.flow.MutableFlower
import io.jackbradshaw.otter.physics.Placement

interface Placeable {
  val placement: MutableFlower<Placement>
}
