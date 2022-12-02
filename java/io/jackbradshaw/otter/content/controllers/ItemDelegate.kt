package io.jackbradshaw.otter.scene.content

import io.jackbradshaw.klu.flow.MutableFlower
import io.jackbradshaw.klu.flow.NiceFlower
import io.jackbradshaw.otter.physics.model.placeZero
import io.jackbradshaw.otter.scene.new.Item

class ItemDelegate(
    override val id: String
) : Item {

  fun captureSnapshot(): Flow<ItemSnapshot>
  suspend fun restoreSnapshot(snapshot: ItemSnapshot)

  fun primitives(): BinaryDeltaFlow<Any>
}