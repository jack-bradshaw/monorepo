package io.jackbradshaw.otter.scene.stage

import com.jme3.bullet.control.GhostControl
import com.jme3.bullet.control.PhysicsControl
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.bullet.joints.PhysicsJoint
import com.jme3.effect.ParticleEmitter
import com.jme3.light.DirectionalLight
import com.jme3.light.Light
import com.jme3.light.PointLight
import com.jme3.light.SpotLight
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.math.model.*
import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.physics.model.minus
import io.jackbradshaw.otter.physics.model.toJMonkeyTransform
import io.jackbradshaw.otter.physics.model.toOtterPlacement
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.scene.item.SceneItem
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import io.jackbradshaw.otter.timing.Clock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO all that's left to do is setup the node sync and then tidy up. then it should work (with
// some tweaking)

/** A basic implementation of [Stage] suitable for use with the delegate pattern. */
class SceneStageImpl(
    private val engine: EngineCore,
    @Rendering private val renderingScope: CoroutineDispatcher,
    @Rendering private val renderingClock: Clock,
    @Physics private val physicsScope: CoroutineDispatcher,
) : SceneStage {

  // TODO consider adding locks for concurrency safety if necessary

  private val itemsPendingIntegration = MutableSharedFlow<DescendantAndAncestor>(replay = 0)
  private val itemsPendingDisintegration = MutableSharedFlow<DescendantAndAncestor>(replay = 0)
  private val elementsPendingIntegration = MutableSharedFlow<ElementAndItem>(replay = 0)
  private val elementsPendingDisintegration = MutableSharedFlow<ElementAndItem>(replay = 0)

  private val itemIntegrationJobs = mutableMapOf<SceneItem, Job>()
  private val elementIntegrationJobs = mutableMapOf<ScenePrimitive, Job>()

  private val topLevelItems = mutableSetOf<SceneItem>()
  private val ancestors = mutableMapOf<SceneItem, SceneItem>()
  private val items = mutableMapOf<ScenePrimitive, SceneItem>()
  private val itemNodes = mutableMapOf<SceneItem, Node>()
  private val physicsControlNodes = mutableMapOf<ScenePrimitive, Node>()

  private val itemEnteredScene = MutableSharedFlow<SceneItem>(replay = 0)
  private val itemExitedScene = MutableSharedFlow<SceneItem>(replay = 0)
  private val elementEnteredScene = MutableSharedFlow<ScenePrimitive>(replay = 0)
  private val elementExitedScene = MutableSharedFlow<ScenePrimitive>(replay = 0)

  override val allItems = items.values.toSet()

  override val allElements = items.keys.toSet()

  override suspend fun findItemByElement(element: ScenePrimitive) = items[element]

  override suspend fun addItem(item: SceneItem) {
    itemsPendingIntegration.tryEmit(DescendantAndAncestor(item, ancestor = null))
  }

  override suspend fun removeItem(item: SceneItem) {
    itemsPendingDisintegration.tryEmit(DescendantAndAncestor(item, ancestor = null))
  }

  override suspend fun removeAllItems() {
    topLevelItems.forEach {
      itemsPendingDisintegration.tryEmit(DescendantAndAncestor(it, ancestor = null))
    }
  }

  override fun elementEnteredScene() = elementEnteredScene

  override fun elementExitedScene() = elementExitedScene

  override fun itemEnteredScene() = itemEnteredScene

  override fun itemExitedScene() = itemExitedScene

  init {
    processItemsPendingIntegration()
    processItemsPendingDisintegration()
    processElementsPendingIntegration()
    processElementsPendingDisintegration()
  }

  private fun processItemsPendingIntegration() {
    engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
      itemsPendingIntegration
          .onEach {
            it.descendant.checkNotAttached()
            it.recordAncestorDescendantNodePair()
            it.recordAncestorDescendantRelationship()
            it.descendant.notifyEnterScene()
            itemIntegrationJobs[it.descendant] = launch {
              launch { it.descendant.queueAddedDescendantsForIntegration() }
              launch { it.descendant.queueRemovedDescendantsForDisintegration() }
              launch { it.descendant.queueAddedElementsForIntegration() }
              launch { it.descendant.queueRemovedElementsForDisintegration() }
              launch { it.descendant.syncPlacement() }
            }
          }
          .collect()
    }
  }

  private fun processItemsPendingDisintegration() {
    engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
      itemsPendingDisintegration
          .onEach {
            it.descendant.checkAttached()
            it.descendant.cancelIntegrationJob()
            it.descendant.queueCurrentDescendantsForDisintegration()
            it.descendant.queueCurrentElementsForDisintegration()
            it.discardAncestorDescendantNodePair()
            it.discardAncestorDescendantRelationship()
            it.descendant.notifyExitScene()
          }
          .collect()
    }
  }

  private fun processElementsPendingIntegration() {
    engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
      elementsPendingDisintegration
          .onEach {
            it.attachToEngine()
            elementIntegrationJobs[it] = launch { launch { it.syncPlacement() } }
          }
          .collect()
    }
  }

  private fun processElementsPendingDisintegration() {
    engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
      elementsPendingDisintegration
          .onEach {
            it.detachFromEngine()
            it.cancelIntegrationJob()
          }
          .collect()
    }
  }

  private suspend fun SceneItem.checkAttached() {
    if (!itemIntegrationJobs.keys.contains(this))
        throw IllegalStateException("Item $this is not attached.")
  }

  private suspend fun SceneItem.checkNotAttached() {
    if (itemIntegrationJobs.keys.contains(this))
        throw IllegalStateException("Item $this is already attached.")
  }

  private suspend fun DescendantAndAncestor.recordAncestorDescendantNodePair() {
    val ancestorNode = itemNodes[ancestor]!!
    val descendantNode = Node().also { itemNodes[descendant] = it }
    ancestorNode.attachChild(descendantNode)
  }

  private suspend fun DescendantAndAncestor.discardAncestorDescendantNodePair() {
    val ancestorNode = itemNodes[ancestor]!!
    val descendantNode = itemNodes[descendant]!!
    ancestorNode.detachChild(descendantNode)
    itemNodes.remove(descendant)
  }

  private suspend fun DescendantAndAncestor.recordAncestorDescendantRelationship() {
    if (ancestor == null) topLevelItems.add(descendant) else ancestors[descendant] = ancestor
  }

  private suspend fun DescendantAndAncestor.discardAncestorDescendantRelationship() {
    ancestors.remove(descendant)
  }

  private suspend fun ElementAndItem.recordItemElementRelationship() {
    items[element] = item
  }

  private suspend fun ElementAndItem.discardElementItemRelationship() {
    items.remove(element)
  }

  private suspend fun SceneItem.notifyEnterScene() {
    onEnterScene()
    itemEnteredScene.tryEmit(this)
  }

  private suspend fun SceneItem.notifyExitScene() {
    onExitScene()
    itemExitedScene.tryEmit(this)
  }

  private suspend fun SceneItem.queueAddedDescendantsForIntegration() =
      descendantAdded()
          .onStart { descendants.forEach { emit(it) } }
          .onEach {
            itemsPendingIntegration.tryEmit(
                DescendantAndAncestor(
                    descendant = it,
                    ancestor = this@queueAddedDescendantsForIntegration,
                ))
          }
          .collect()

  private suspend fun SceneItem.queueRemovedDescendantsForDisintegration() =
      descendantRemoved()
          .onEach {
            itemsPendingDisintegration.tryEmit(
                DescendantAndAncestor(
                    descendant = it,
                    ancestor = this@queueRemovedDescendantsForDisintegration,
                ))
          }
          .collect()

  private suspend fun SceneItem.queueCurrentDescendantsForDisintegration() {
    descendants.forEach {
      itemsPendingDisintegration.tryEmit(DescendantAndAncestor(it, ancestor = ancestors[it]!!))
    }
  }

  private suspend fun SceneItem.queueAddedElementsForIntegration() =
      elementAdded()
          .onStart { elements.forEach { emit(it) } }
          .onEach {
            elementsPendingIntegration.tryEmit(
                ElementAndItem(it, this@queueAddedElementsForIntegration))
          }
          .collect()

  private suspend fun SceneItem.queueRemovedElementsForDisintegration() =
      elementRemoved()
          .onEach {
            elementsPendingDisintegration.tryEmit(
                ElementAndItem(it, this@queueRemovedElementsForDisintegration))
          }
          .collect()

  private suspend fun SceneItem.queueCurrentElementsForDisintegration() {
    elements.forEach { elementsPendingDisintegration.tryEmit(ElementAndItem(it, items[it]!!)) }
  }

  private suspend fun ScenePrimitive.attachToEngine() {
    when (val element = this) {
      is Light -> withContext(renderingScope) { engine.extractGameNode().addLight(element) }
      is ParticleEmitter ->
          withContext(renderingScope) { engine.extractGameNode().attachChild(element) }
      is Spatial -> withContext(renderingScope) { engine.extractGameNode().attachChild(element) }
      is PhysicsControl ->
          withContext(physicsScope) {
            element.newNode().apply { addControl(element) }
            engine.extractPhysics().physicsSpace.add(element)
          }
      is PhysicsJoint ->
          withContext(physicsScope) { engine.extractPhysics().physicsSpace.add(element) }
      else ->
          throw UnsupportedOperationException(
              "Cannot process $element. Type is currently unsupported.")
    }
  }

  private suspend fun ScenePrimitive.detachFromEngine() {
    when (val element = this) {
      is Light -> withContext(renderingScope) { engine.extractGameNode().removeLight(element) }
      is ParticleEmitter ->
          withContext(renderingScope) { engine.extractGameNode().detachChild(element) }
      is Spatial -> withContext(renderingScope) { engine.extractGameNode().detachChild(element) }
      is PhysicsControl ->
          withContext(physicsScope) {
            physicsControlNodes[element]!!.removeControl(element)
            engine.extractPhysics().physicsSpace.remove(element)
          }
      is PhysicsJoint ->
          withContext(physicsScope) { engine.extractPhysics().physicsSpace.remove(element) }
      else ->
          throw UnsupportedOperationException(
              "Cannot process $element. Type is currently unsupported.")
    }
  }

  private suspend fun ScenePrimitive.newNode() =
      Node().also {
        physicsControlNodes[this] = it
        engine.extractGameNode().attachChild(it)
      }

  private suspend fun SceneItem.syncPlacement() = withContext(renderingScope) {
    val itemNode = itemNodes[this@syncPlacement]!!
    launch {
      itemNode
        .placement()
          .distinctUntilChanged()
          .onEach { placeAt(it) }
          .collect()
    }
    launch {
      placement()
          .onEach { itemNode.localTransform = it.toJMonkeyTransform() }
          .collect()
    }
  }

  // TODO check this is the right vector to use for quaternion to direction
  private suspend fun ScenePrimitive.syncPlacement() {
    val item = items[this]!!
    val itemNode = itemNodes[item]!!
    when (this) {
      is PointLight ->
          withContext(renderingScope) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { position = it.position.toJMonkeyVector() }
                .collect()
          }
      is DirectionalLight ->
          withContext(renderingScope) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { direction = unitXVector.rotateBy(it.rotation).toJMonkeyVector() }
                .collect()
          }
      is SpotLight ->
          withContext(renderingScope) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach {
                  position = it.position.toJMonkeyVector()
                  direction = unitXVector.rotateBy(it.rotation).toJMonkeyVector()
                }
                .collect()
          }
      is ParticleEmitter ->
          withContext(renderingScope) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { localTransform = it.toJMonkeyTransform() }
                .collect()
          }
      is Spatial ->
          withContext(renderingScope) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { localTransform = it.toJMonkeyTransform() }
                .collect()
          }
      is RigidBodyControl ->
          withContext(physicsScope) {
            val physicsNode = physicsControlNodes[this@syncPlacement]!!
            launch {
              physicsNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .map { (it - itemNode.parent.worldTransform.toOtterPlacement()).toJMonkeyTransform() }
                  .onEach { itemNode.localTransform = it }
                  .collect()
            }
            launch {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .onEach {
                    this@syncPlacement.physicsLocation = it.position.toJMonkeyVector()
                    this@syncPlacement.physicsRotation = it.rotation.toJMonkeyQuaternion()
                  }
                  .collect()
            }
          }
      is GhostControl ->
          withContext(physicsScope) {
            val physicsNode = physicsControlNodes[this@syncPlacement]!!
            launch {
              physicsNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .map { (it - itemNode.parent.worldTransform.toOtterPlacement()).toJMonkeyTransform() }
                  .onEach { itemNode.localTransform = it }
                  .collect()
            }
            launch {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .onEach {
                    this@syncPlacement.physicsLocation = it.position.toJMonkeyVector()
                    this@syncPlacement.physicsRotation = it.rotation.toJMonkeyQuaternion()
                  }
                  .collect()
            }
          }
    }
  }

  private suspend fun Node.placement() =
      renderingClock
          .deltaSec()
          .map { localTransform }
          .distinctUntilChanged()
          .map { it.toOtterPlacement() }

  private suspend fun Node.absolutePlacement(): Flow<Placement> =
      renderingClock
          .deltaSec()
          .map { worldTransform }
          .distinctUntilChanged()
          .map { it.toOtterPlacement() }

  private suspend fun SceneItem.cancelIntegrationJob() {
    itemIntegrationJobs.remove(this)?.also { it.cancel() }
  }

  private suspend fun ScenePrimitive.cancelIntegrationJob() {
    elementIntegrationJobs.remove(this)?.also { it.cancel() }
  }
}

/** A [descendant] and its [ancestor] (null for top-level items). */
private class DescendantAndAncestor(val descendant: SceneItem, val ancestor: SceneItem?)

/** A scene [element] and its enclosing [item]. */
private class ElementAndItem(val element: ScenePrimitive, val item: SceneItem)
