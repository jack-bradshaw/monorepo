package io.matthewbradshaw.jockstrap.model.elements

import io.matthewbradshaw.jockstrap.model.frames.Hostable
import io.matthewbradshaw.jockstrap.model.frames.Placeable
import io.matthewbradshaw.jockstrap.model.frames.Restorable

interface Component<I> : Hostable, Placeable, Restorable<ComponentSnapshot> {
  val id: ComponentId
  val source: Entity
  val intrinsic: I
}