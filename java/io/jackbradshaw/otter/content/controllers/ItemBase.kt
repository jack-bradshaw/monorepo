package io.jackbradshaw.otter.scene.content

import io.jackbradshaw.klu.flow.MutableFlower
import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.otter.physics.model.Placement
import com.google.protobuf.MessageLite
import kotlinx.coroutines.CoroutineScope

class ItemBase : Item {
  override val id: String = TODO()
  override fun primitives(): Set<Primitive> = TODO()
  override fun primitiveAdded(): Flow<Primitive> = TODO()
  override fun primitiveRemoved(): Flow<Primitive> = TODO()
}