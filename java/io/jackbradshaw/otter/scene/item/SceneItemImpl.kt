package io.jackbradshaw.otter.scene.item

import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.physics.model.placeZero
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A basic implementation of [SceneItem] that can be customized by the constructor parameters.
 */
class ParameterizedSceneItem(
    override var id: String,
    var onEnterStage: () -> Unit,
    var onExitStage: () -> Unit
) : SceneItem {

  override val elements = mutableSetOf<ScenePrimitive>()
  override val descendants = mutableSetOf<SceneItem>()

  private val placement = MutableStateFlow(placeZero)
  override fun placement() = placement

  override suspend fun placeAt(place: Placement) {
    placement.tryEmit(place)
  }

  private val descendantAdded = MutableSharedFlow<SceneItem>(replay = 0)
  override fun descendantAdded() = descendantAdded

  private val descendantRemoved = MutableSharedFlow<SceneItem>(replay = 0)
  override fun descendantRemoved() = descendantRemoved

  private val elementAdded = MutableSharedFlow<ScenePrimitive>(replay = 0)
  override fun elementAdded() = elementAdded

  private val elementRemoved = MutableSharedFlow<ScenePrimitive>(replay = 0)
  override fun elementRemoved() = elementRemoved

  override suspend fun addDescendant(descendant: SceneItem, relativePlacement: Placement) {
    descendant.placeAt(relativePlacement)
    descendants.add(descendant)
    descendantAdded.tryEmit(descendant)
  }

  override suspend fun removeDescendant(descendant: SceneItem) {
    descendants.remove(descendant)
    descendantRemoved.tryEmit(descendant)
  }

  override suspend fun addElement(element: ScenePrimitive) {
    elements.add(element)
    elementAdded.tryEmit(element)
  }

  override suspend fun removeElement(element: ScenePrimitive) {
    elements.remove(element)
    elementRemoved.tryEmit(element)
  }

  override suspend fun onEnterStage() = onEnterStage.invoke()

  override suspend fun onExitStage() = onExitStage.invoke()
}
