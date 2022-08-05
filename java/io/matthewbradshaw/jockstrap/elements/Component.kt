package io.matthewbradshaw.jockstrap.elements

import io.matthewbradshaw.jockstrap.frames.Hostable
import io.matthewbradshaw.jockstrap.frames.Placeable
import io.matthewbradshaw.jockstrap.frames.Simulatable
import io.matthewbradshaw.jockstrap.frames.Restorable
import com.google.protobuf.MessageLite
import kotlinx.coroutines.flow.Flow

interface Component<I> : Hostable, Placeable, Restorable<ComponentSnapshot>, Simulatable {
  val id: ComponentId
  val source: Entity

  fun intrinsic(): Flow<I>
}