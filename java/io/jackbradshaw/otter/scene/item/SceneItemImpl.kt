package io.jackbradshaw.otter.scene.item

import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.physics.model.placeZero
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

/** A basic implementation of [SceneItem] that can be customized by the constructor parameters. */
abstract class SceneItemImpl : SceneItem {

  override val elements = mutableSetOf<ScenePrimitive>()
  override val descendants = mutableSetOf<SceneItem>()

  private val placement = MutableStateFlow(placeZero)

  override fun placement() = placement

  override suspend fun placeAt(place: Placement) {
    placement.value = place
  }

  override suspend fun updatePlace(update: (Placement) -> Placement) {
    placeAt(update(placement.first()))
  }

  private val descendantAdded = MutableSharedFlow<SceneItem>(replay = 0)

  override fun descendantAdded() = descendantAdded

  private val elementAdded = MutableSharedFlow<ScenePrimitive>(replay = 0)

  override fun elementAdded() = elementAdded

  private val descendantRemoved = MutableSharedFlow<SceneItem>(replay = 0)

  override fun descendantRemoved() = descendantRemoved

  private val elementRemoved = MutableSharedFlow<ScenePrimitive>(replay = 0)

  override fun elementRemoved() = elementRemoved

  override suspend fun addDescendant(descendant: SceneItem, relativePlacement: Placement) {
    descendant.placeAt(relativePlacement)
    descendants.add(descendant)
    descendantAdded.tryEmit(descendant)
  }

  override suspend fun addElement(element: ScenePrimitive) {
    elements.add(element)
    elementAdded.tryEmit(element)
  }

  override suspend fun removeDescendant(descendant: SceneItem) {
    descendants.remove(descendant)
    descendantRemoved.tryEmit(descendant)
  }

  override suspend fun removeElement(element: ScenePrimitive) {
    elements.remove(element)
    elementRemoved.tryEmit(element)
  }

  override suspend fun onEnterScene() {
    // NO-OP
  }

  override suspend fun onExitScene() {
    // NO-OP
  }
}
