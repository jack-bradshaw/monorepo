package io.jackbradshaw.otter.structure.controllers

import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.otter.structure.frames.Hostable
import io.jackbradshaw.otter.structure.frames.Placeable
import io.jackbradshaw.otter.structure.frames.Restorable

interface Item : Hostable<Component>, Placeable, Restorable<ItemSnapshot> {

  val id: ItemId

  fun integrations(): BinaryDeltaFlow<Integration>

  fun engineElements(): BinaryDeltaFlow<Any>
}