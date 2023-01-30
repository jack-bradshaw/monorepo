package io.jackbradshaw.otter.scene.stage

import io.jackbradshaw.otter.physics.model.toOtterPlacement
import com.jme3.math.Transform
import com.jme3.scene.Node
import com.jme3.scene.control.AbstractControl
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.physics.model.toJMonkeyTransform
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.scene.item.SceneItem
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import io.jackbradshaw.otter.timing.Clock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


/**
 * A basic implementation of [Stage] suitable for use with the delegate pattern.
 */
class SceneStageImpl(
    private val engine: EngineCore,
    @Rendering private val renderingScope: CoroutineDispatcher,
    @Rendering private val renderingClock: Clock,
    @Physics private val physicsScope: CoroutineDispatcher,
) : SceneStage {

  // TODO consider adding locks for concurrency safety if necessary
  private val itemsPendingIntegration = MutableSharedFlow<ItemAndAncestor>(replay = 0)
  private val itemsPendingDisintegration = MutableSharedFlow<ItemAndAncestor>(replay = 0)
  private val elementsPendingIntegration = MutableSharedFlow<ElementAndAncestor>(replay = 0)
  private val elementsPendingDisintegration = MutableSharedFlow<ElementAndAncestor>(replay = 0)
  private val itemIntegrationJobs = mutableMapOf<SceneItem, Job>()
  private val elementIntegrationJobs = mutableMapOf<ScenePrimitive, Job>()
  private val itemAncestors = mutableMapOf<SceneItem, SceneItem>()
  private val elementAncestors = mutableMapOf<ScenePrimitive, SceneItem>()
  private val topLevelItems = mutableSetOf<SceneItem>()
  private val nodes = mutableMapOf<SceneItem, Node>()

  init {
    processItemsPendingIntegration()
    processItemsPendingDisintegration()
    processElementsPendingIntegration()
    processElementsPendingDisintegration()
  }

  // 1 what are the things to do for each item pending integration?
  // 1.1 DONE monitor the added descendants and queue them for integration
  // 1.2 DONE monitor the removed descendants and queue them for disintegration
  // 1.3 DONE monitor the added elements and queue them for integration
  // 1.4 DONE monitor the removed elements and queue them for disintegration
  // 1.6 DONE set-up the node relationship for the item
  // 1.7 DONE sync placement with node transform bidirectionally

  // TODO cleanup and integrate big logic list
  private fun processItemsPendingIntegration() = engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
    itemsPendingIntegration.onEach {
      it.item.checkNotAttached()
      it.createNodeRelationship()
      it.recordAncestorRelationship()
      it.item.notifyEnterScene()
      itemIntegrationJobs[it.item] = launch {
        launch { it.item.queueAddedDescendantsForIntegration() }
        launch { it.item.queueRemovedDescendantsForDisintegration() }
        launch { it.item.queueAddedElementsForIntegration() }
        launch { it.item.queueRemovedElementsForDisintegration() }
        launch { it.item.syncPlacementWithNode() }
      }
    }.collect()
  }

  // 3 what are the things to do for each item disintegration?
  // 3.1 DONE queue the descendants for disintegration
  // 3.2 DONE queue the elements for disintegration
  // 3.3 DONE cancel ongoing jobs
  // 3.4 DONE delete the node

  // TODO cleanup and integrate big logic list
  private fun processItemsPendingDisintegration() = engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
    itemsPendingDisintegration.onEach {
      it.item.checkAttached()
      it.item.cancelIntegrationJob()
      it.item.queueCurrentDescendantsForDisintegration()
      it.item.queueCurrentElementsForDisintegration()
      it.deleteNodeRelationship()
      it.discardAncestorRelationship()
      it.item.notifyExitScene()
    }.collect()
  }

  // 2 what are the things to do for each element pending integration?
  // 2.1 DONE add the item to the engine
  // 2.2 DONE sync placement with node transform bidirectionally (if necessary)

  private fun processElementsPendingIntegration() = engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
    elementsPendingDisintegration.onEach {
      it.attachToEngine()
      elementIntegrationJobs[it.element] = launch { it.syncPlacementWithNode() }
    }.collect()
  }

  // 4. what are the things to do for each element disintegration?
  // 4.1 cancel placement sync
  // 4.2 remove the item from the engine

  private fun processElementsPendingDisintegration() = engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
    elementsPendingDisintegration.onEach {
      it.element.cancelIntegrationJob()
      it.detachFromEngine()
    }.collect()
  }

  private suspend fun SceneItem.checkNotAttached() {
    if (isAttached()) throw IllegalStateException("Item $this is already attached. Cannot attach it again.")
  }

  private suspend fun SceneItem.checkAttached() {
    if (!isAttached()) throw IllegalStateException("Item $this is not attached. Cannot detach it.")
  }

  private suspend fun SceneItem.isAttached() = itemIntegrationJobs.keys.contains(this)

  private suspend fun ItemAndAncestor.recordAncestorRelationship() {
    if (ancestor == null) topLevelItems.add(item) else itemAncestors[item] = ancestor
  }

  private suspend fun ElementAndAncestor.recordAncestorRelationship() {
    elementAncestors[element] = ancestor
  }

  private suspend fun ItemAndAncestor.discardAncestorRelationship() {
    itemAncestors.remove(item)
  }

  private suspend fun ElementAndAncestor.discardAncestorRelationship() {
    elementAncestors.remove(element)
  }

  private suspend fun ItemAndAncestor.createNodeRelationship() {
    val ancestorNode = nodes[ancestor]!!
    val descendantNode = Node().also { nodes[item] = it }
    ancestorNode.attachChild(descendantNode)
  }

  private suspend fun ItemAndAncestor.deleteNodeRelationship() {
    val ancestorNode = nodes[ancestor]!!
    val descendantNode = nodes[item]!!
    ancestorNode.detachChild(descendantNode)
    nodes.remove(item)
  }

  private suspend fun ScenePrimitive.attachToEngine() {

  }

  private suspend fun ScenePrimitive.detachFromEngine() {

  }

  private suspend fun SceneItem.notifyEnterScene() {
    onEnterScene()
    itemEnteredScene.tryEmit(this)
  }

  private suspend fun SceneItem.notifyExitScene() {
    onExitScene()
    itemExitedScene.tryEmit(this)
  }

  private suspend fun SceneItem.queueAddedDescendantsForIntegration() = descendantAdded().onStart { descendants.forEach { emit(it) } }.onEach {
    itemsPendingIntegration.tryEmit(ItemAndAncestor(
        item = it,
        ancestor = this@queueAddedDescendantsForIntegration,
    ))
  }.collect()

  private suspend fun SceneItem.queueRemovedDescendantsForDisintegration() = descendantRemoved().onEach {
    itemsPendingDisintegration.tryEmit(ItemAndAncestor(
        item = it,
        ancestor = this@queueRemovedDescendantsForDisintegration,
    ))
  }.collect()

  private suspend fun SceneItem.queueCurrentDescendantsForDisintegration() {
    descendants.forEach { itemsPendingDisintegration.tryEmit(ItemAndAncestor(it, ancestor = itemAncestors[it]!!)) }
  }

  private suspend fun SceneItem.queueAddedElementsForIntegration() = elementAdded()
      .onStart { elements.forEach { emit(it) } }
      .onEach { elementsPendingIntegration.tryEmit(ElementAndAncestor(it, this@queueAddedElementsForIntegration)) }
      .collect()

  private suspend fun SceneItem.queueRemovedElementsForDisintegration() = elementRemoved()
      .onEach {
        elementsPendingDisintegration.tryEmit(ElementAndAncestor(it, this@queueRemovedElementsForDisintegration))
      }
      .collect()

  private suspend fun SceneItem.queueCurrentElementsForDisintegration() {
    elements.forEach { elementsPendingDisintegration.tryEmit(ElementAndAncestor(it, elementAncestors[it]!!)) }
  }

  private suspend fun SceneItem.syncPlacementWithNode() {
    val node = nodes[this]!!
    // TODO consider debouncing somehow (empty map for comment placeholder)
    return merge(node.placement(), placement()).onEach {
      node.setLocalTransform(it.toJMonkeyTransform())
      placeAt(it)
    }.collect()
  }

  private suspend fun ScenePrimitive.syncPlacementWithNode() {
    if (requiresAttachmentToRoot()) {
      TODO()
    } else {
      TODO()
    }
  }

  private suspend fun SceneItem.cancelIntegrationJob() {
    itemIntegrationJobs.remove(this)?.also { it.cancel() }
  }

  private suspend fun ScenePrimitive.cancelIntegrationJob() {
    elementIntegrationJobs.remove(this)?.also { it.cancel() }
  }


  // TODO use or delete all below here
  private val primitivesToItems = mutableMapOf<ScenePrimitive, SceneItem>()
  private val idsToItems = mutableMapOf<String, Set<SceneItem>>()

  override val allItems = primitivesToItems.values.toSet()

  override val allElements = primitivesToItems.keys

  override suspend fun findItemByElement(element: ScenePrimitive) = primitivesToItems[element]

  override suspend fun findItemById(id: String) = idsToItems[id] ?: setOf<SceneItem>()

  override suspend fun findUniqueItemById(id: String): SceneItem {
    // TODO tidy up
    idsToItems[id] ?: throw IllegalStateException("No item found for id $id")
    if (idsToItems[id]!!.size > 1) throw IllegalStateException("Multiple items found for id $id. Use findItemById if uniqueness is not necessary.")
    return idsToItems[id]!!.toList()[0]
  }

  override suspend fun addItem(item: SceneItem) {
    itemsPendingIntegration.tryEmit(ItemAndAncestor(item, ancestor = null))
  }

  override suspend fun removeItem(item: SceneItem) {
    itemsPendingDisintegration.tryEmit(ItemAndAncestor(item, ancestor = null))
  }

  override suspend fun removeAllItems() {
    topLevelItems.forEach { itemsPendingDisintegration.tryEmit(ItemAndAncestor(it, ancestor = null)) }
  }

  private val elementEnteredScene = MutableSharedFlow<ScenePrimitive>(replay = 0)
  override fun elementEnteredScene() = elementEnteredScene

  private val elementExitedScene = MutableSharedFlow<ScenePrimitive>(replay = 0)
  override fun elementExitedScene() = elementExitedScene

  private val itemEnteredScene = MutableSharedFlow<SceneItem>(replay = 0)
  override fun itemEnteredScene() = itemEnteredScene

  private val itemExitedScene = MutableSharedFlow<SceneItem>(replay = 0)
  override fun itemExitedScene() = itemExitedScene

  private val controllers = mapOf<ScenePrimitive, AbstractControl>()

  private suspend fun ScenePrimitive.placeAt(transform: Transform) {
    // TODO update placement of scene primitive to match transform in global coordinates
    // similar to the functions below
  }

  private suspend fun Node.placement() = renderingClock.deltaSec().map { worldTransform }.distinctUntilChanged().map { it.toOtterPlacement() }

  private suspend fun ScenePrimitive.requiresAttachmentToRoot(): Boolean = TODO() // Logic to determine if a primitive needs to be attached to the root or its immediate ancestor

  /*

    private suspend fun ScenePrimitive.attachToGameNode() = when (this) {
      is Light -> withContext(renderingScope) {
        engine.extractGameNode().addLight(element)
      }
      is ParticleEmitter -> withContext(renderingScope) {
        engine.extractGameNode().attachChild(element)
      }
      is SceneProcessor -> withContext(renderingScope) {
        engine.extractDefaultViewPort().addProcessor(element)
      }
      is Spatial -> withContext(renderingScope) {
        engine.extractGameNode().attachChild(element)
      }
      is BetterCharacterControl -> withContext(physicsScope) {
        engine.extractPhysics().getPhysicsSpace().add(element)
      }
      is PhysicsCollisionObject -> withContext(physicsScope) {
        engine.extractPhysics().getPhysicsSpace().add(element)
      }
      is PhysicsJoint -> withContext(physicsScope) {
        engine.extractPhysics().getPhysicsSpace().add(element)
      }
      else -> throw UnsupportedOperationException("Integration has not been implemented for $this.")
    }

    private suspend fun ScenePrimitive.detachFromGameNode() = when (this) {
      is Light -> withContext(renderingScope) {
        engine.extractGameNode().removeLight(this)
        anchor.removeControl(controllers[this]!!)
      }
      is ParticleEmitter -> withContext(renderingScope) {
        engine.extractGameNode().detachChild(this)
      }
      is SceneProcessor -> withContext(renderingScope) {
        engine.extractDefaultViewPort().removeProcessor(this)
      }
      is Spatial -> withContext(renderingScope) {
        engine.extractGameNode().detachChild(this)
      }
      is BetterCharacterControl -> withContext(physicsScope) {
        engine.extractPhysics().getPhysicsSpace().remove(this)
      }
      is PhysicsCollisionObject -> withContext(physicsScope) {
        engine.extractPhysics().getPhysicsSpace().remove(this)
      }
      is PhysicsJoint -> withContext(physicsScope) {
        engine.extractPhysics().getPhysicsSpace().remove(this)
      }
      else -> throw UnsupportedOperationException("Disintegration has not been implemented for $this.")
    }*/
}

private class ItemAndAncestor(val item: SceneItem, val ancestor: SceneItem?) // null for top-level items)
private class ElementAndAncestor(val element: ScenePrimitive, val ancestor: SceneItem)
