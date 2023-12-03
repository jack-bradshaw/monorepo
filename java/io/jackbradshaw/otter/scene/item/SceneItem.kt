package io.jackbradshaw.otter.scene.item

import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.physics.model.placeZero
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import kotlinx.coroutines.flow.Flow

/**
 * A self-contained entity in a scene consisting of atomic elements (primitives which cannot be
 * decomposed into items) and descendants items. Descendants are placed relative to this item.
 */
interface SceneItem :
    SceneItemSceneProperties, SceneItemMutations, SceneItemEvents, SceneItemLifecycle

/** Properties of a [SceneItem]. */
interface SceneItemSceneProperties {
  val elements: Set<ScenePrimitive>
  val descendants: Set<SceneItem>

  fun placement(): Flow<Placement>
}

/** Mutations that can be performed on a [SceneItem]. */
interface SceneItemMutations {

  suspend fun placeAt(place: Placement)

  suspend fun updatePlace(update: (Placement) -> Placement)

  suspend fun addDescendant(descendant: SceneItem, relativePlacement: Placement = placeZero)

  suspend fun addElement(element: ScenePrimitive)

  suspend fun removeDescendant(descendant: SceneItem)

  suspend fun removeElement(element: ScenePrimitive)
}

/** Observable events emitted by a [SceneItem]. */
interface SceneItemEvents {
  fun descendantAdded(): Flow<SceneItem>

  fun descendantRemoved(): Flow<SceneItem>

  fun elementAdded(): Flow<ScenePrimitive>

  fun elementRemoved(): Flow<ScenePrimitive>
}

/** The lifecycle of a [SceneItem]. */
interface SceneItemLifecycle {
  suspend fun onEnterScene()

  suspend fun onExitScene()
}
