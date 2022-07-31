package io.matthewbradshaw.jockstrap.model.elements

import io.matthewbradshaw.jockstrap.model.frames.Hostable
import io.matthewbradshaw.jockstrap.model.frames.Placeable
import io.matthewbradshaw.jockstrap.model.frames.Simulatable
import io.matthewbradshaw.jockstrap.model.frames.Restorable
import com.google.protobuf.MessageLite
import kotlinx.coroutines.flow.Flow

interface Component<I> : Hostable, Placeable, Restorable<ComponentSnapshot>, Simulatable {
  val id: ComponentId
  val source: Entity

  fun intrinsic(): Flow<I>
}