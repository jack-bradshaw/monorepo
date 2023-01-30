package io.jackbradshaw.otter.scene.item

import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.physics.model.placeZero
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import kotlinx.coroutines.flow.Flow

/**
 * A self-contained entity in a scene consisting of atomic elements (primitives which cannot be decomposed into props)
 * and descendants (other props that are part of this one).
 *
 * Generally descendants follow the placement of the ancestor, but this is neither required nor guaranteed. This means
 * descendants may have their own placement that is unaffected by the placement of their ancestor, or is sometimes
 * affected by the placement of their ancestor and othertimes not. This allows for mechanics such as spawners and other
 * entities where some descendant are logically disconnected from their ancestor once the player interacts with them.
 * For example, consider a prop that spawns grenades when the user presses a button. Initially it makes sense for them
 * to move with the spawner, but once the player has picked them up and moved them around the scene it no longer makes
 * logical sense for them to move with the spawner. To ensure the logic remains flexible and open, tethering and
 * untethering is left as an implementation detail. One caveat of this approach is that descendants are removed from the
 * stage when the ancestor is removed, meaning in the example grenades would be removed once the spawner is removed. If
 * this is undesirable, such desendants should instead be owned and coordinated by a common ancestor of the spawner.
 */
interface SceneItem : SceneItemSceneProperties, SceneItemMutations, SceneItemEvents, SceneItemLifecycle

/**
 * Properties of a [SceneItem].
 */
interface SceneItemSceneProperties {
  val elements: Set<ScenePrimitive>
  val descendants: Set<SceneItem>
  fun placement(): Flow<Placement>
}

/**
 * Mutations that can be performed on a [SceneItem].
 */
interface SceneItemMutations {
  val id: String

  suspend fun placeAt(place: Placement)

  suspend fun addDescendant(descendant: SceneItem, relativePlacement: Placement = placeZero)

  suspend fun removeDescendant(descendant: SceneItem)

  suspend fun addElement(element: ScenePrimitive)

  suspend fun removeElement(element: ScenePrimitive)
}

/**
 * Observable events emitted by a [SceneItem].
 */
interface SceneItemEvents {
  fun descendantAdded(): Flow<SceneItem>
  fun descendantRemoved(): Flow<SceneItem>
  fun elementAdded(): Flow<ScenePrimitive>
  fun elementRemoved(): Flow<ScenePrimitive>
}

/**
 * The lifecycle of a [SceneItem].
 */
interface SceneItemLifecycle {
  suspend fun onEnterScene()
  suspend fun onExitScene()
}