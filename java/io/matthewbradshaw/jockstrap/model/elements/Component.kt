package io.matthewbradshaw.jockstrap.model.elements

import io.matthewbradshaw.jockstrap.model.frames.Hostable
import io.matthewbradshaw.jockstrap.model.frames.Placeable
import io.matthewbradshaw.jockstrap.model.frames.Restorable
import google.protobuf.MessageLite

interface Component<I, S : MessageLite> : Hostable, Placeable, Restorable<S> {
  val id: ComponentId
  val source: Entity
  val intrinsic: I
}