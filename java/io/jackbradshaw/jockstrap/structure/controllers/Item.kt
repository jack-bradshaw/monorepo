package io.jackbradshaw.jockstrap.structure.controllers

import io.jackbradshaw.jockstrap.structure.frames.Hostable
import io.jackbradshaw.jockstrap.structure.frames.Placeable
import io.jackbradshaw.jockstrap.structure.frames.Restorable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.klu.flow.Flower

interface Item : Hostable<Component>, Placeable, Restorable<ItemSnapshot> {

  val id: ItemId

  fun constituents(): BinaryDeltaFlow<Primitive>
}