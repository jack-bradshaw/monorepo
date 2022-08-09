package io.jackbradshaw.jockstrap.elements

import io.jackbradshaw.jockstrap.frames.Hostable
import io.jackbradshaw.jockstrap.frames.Placeable
import io.jackbradshaw.jockstrap.frames.Simulatable
import io.jackbradshaw.jockstrap.frames.Restorable
import com.google.protobuf.MessageLite
import kotlinx.coroutines.flow.Flow

interface Component<I> : Hostable, Placeable, Restorable<ComponentSnapshot>, Simulatable {
  val id: ComponentId
  val source: Entity

  fun intrinsic(): Flow<I>
}