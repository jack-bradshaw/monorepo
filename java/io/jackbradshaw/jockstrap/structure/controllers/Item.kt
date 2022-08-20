package io.jackbradshaw.jockstrap.structure.controllers

import io.jackbradshaw.jockstrap.structure.frames.Hostable
import io.jackbradshaw.jockstrap.structure.frames.Placeable
import io.jackbradshaw.jockstrap.structure.frames.Restorable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.jockstrap.structure.controllers.Integration

interface Item : Hostable<Component>, Placeable, Restorable<ItemSnapshot> {

  val id: ItemId

  fun integrations(): BinaryDeltaFlow<Integration>

  fun engineElements(): BinaryDeltaFlow<Any>
}