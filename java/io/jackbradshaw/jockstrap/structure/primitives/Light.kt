package io.jackbradshaw.jockstrap.structure.primitives

import io.jackbradshaw.jockstrap.graphics.Color
import io.jackbradshaw.klu.flow.Flower

interface Light : io.jackbradshaw.jockstrap.structure.controllers.Primitive<Light> {
  val color: Flower<Color>
  val behavior: Flower<LightingComponentBehavior>
}
