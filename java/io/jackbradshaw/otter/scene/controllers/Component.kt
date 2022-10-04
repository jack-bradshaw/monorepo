package io.jackbradshaw.otter.structure.controllers

import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.otter.structure.frames.Hostable
import io.jackbradshaw.otter.structure.frames.Placeable

interface Component : Hostable<Level>, Placeable {

  val id: ComponentId

  fun items(): BinaryDeltaFlow<Item>

  fun subcomponents(): BinaryDeltaFlow<Component>
}
