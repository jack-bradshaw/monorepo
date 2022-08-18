package io.jackbradshaw.jockstrap.structure.controllers

import io.jackbradshaw.jockstrap.structure.frames.Hostable
import io.jackbradshaw.jockstrap.structure.frames.Placeable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.klu.flow.Flower

sealed interface Component : Hostable<Level>, Placeable {
  val id: ComponentId
  fun items(): BinaryDeltaFlow<Item>
  fun subcomponents(): BinaryDeltaFlow<Component>
}