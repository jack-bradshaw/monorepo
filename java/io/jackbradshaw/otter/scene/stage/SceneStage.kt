package io.jackbradshaw.otter.scene.stage

import io.jackbradshaw.klu.flow.ColdFlow
import io.jackbradshaw.klu.flow.IndefiniteFlow
import io.jackbradshaw.otter.scene.item.SceneItem
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import kotlinx.coroutines.flow.Flow

/** Hosts and presents a scene made of [SceneItem]s. */
interface SceneStage :
    SceneStageProperties, SceneStageServices, SceneStageMutations, SceneStageEvents

/** Properties of a [SceneStage]. */
interface SceneStageProperties {
  val allElements: Set<ScenePrimitive>

  /** A flat set of all items that are transitively present on the stage. */
  val allItems: Set<SceneItem>
}

/** Services provided by a [SceneStage]. */
interface SceneStageServices {
  /**
   * Returns the item that has [element] as one of its immediate elements, null if none is found.
   */
  suspend fun findItemByElement(element: ScenePrimitive): SceneItem?
}

/** Mutations that can be performed on a [SceneStage]. */
interface SceneStageMutations {

<<<<<<< HEAD
  /**
   * Adds [item] and to the stage.
   */
=======
  /** Adds [item] and to the stage. */
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
  suspend fun addItem(item: SceneItem)

  /** Removes [item] from the stage. */
  suspend fun removeItem(item: SceneItem)

  /** Removes all current items from the stage. */
  suspend fun removeAllItems()
}

/** Observable events emitted by a [SceneStage]. */
interface SceneStageEvents {
  /**
   * A flow which emits elements as they are attached to the stage. Ordering is not guaranteed to be
   * consistent.
   */
  @ColdFlow @IndefiniteFlow fun elementEnteredScene(): Flow<ScenePrimitive>

  /**
   * A flow which emits elements as they are detached from the stage. Ordering is not guaranteed to
   * be consistent.
   */
  @ColdFlow @IndefiniteFlow fun elementExitedScene(): Flow<ScenePrimitive>

  /**
   * A flow which emits props as they are attached to the stage (directly or transitively via
   * another item). Ordering is not guaranteed to be consistent.
   */
  @ColdFlow @IndefiniteFlow fun itemEnteredScene(): Flow<SceneItem>

  /**
   * A flow which emits props as they are detached from the stage (directly or transitively via
   * another item). Ordering is not guaranteed to be consistent.
   */
  @ColdFlow @IndefiniteFlow fun itemExitedScene(): Flow<SceneItem>
}
