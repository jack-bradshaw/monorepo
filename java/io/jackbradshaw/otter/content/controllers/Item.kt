package io.jackbradshaw.otter.scene.content

import com.google.protobuf.MessageLite
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.CoroutineScope
import io.jackbradshaw.klu.flow.MutableFlower
import io.jackbradshaw.otter.physics.model.Placement

interface Item {
  fun getPlace(): Flow<Placement>
  suspend fun placeAt(place: Placement)

  suspend fun primitives(): Set<Primitive>
  fun primitiveAdded(): Flow<Primitive>
  fun primitiveRemoved(): Flow<Primitive>
}

typealias Primitive = Any