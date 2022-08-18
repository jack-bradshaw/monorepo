package io.jackbradshaw.jockstrap.structure.primitives

import io.jackbradshaw.klu.flow.Flower

interface Appearance : io.jackbradshaw.jockstrap.structure.controllers.Primitive<Appearance> {
  val appearance: Flower<Appearance>
}
