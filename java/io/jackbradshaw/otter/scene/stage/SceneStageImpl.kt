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
import io.jackbradshaw.otter.math.model.toJMonkeyQuaternion
import io.jackbradshaw.otter.math.model.toJMonkeyVector
import io.jackbradshaw.otter.physics.model.relativeTo
import io.jackbradshaw.otter.physics.model.toJMonkeyTransform
import io.jackbradshaw.otter.physics.model.toOtterPlacement
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.scene.item.SceneItem
import io.jackbradshaw.otter.scene.primitive.ScenePrimitive
import io.jackbradshaw.otter.timing.Clock
import java.lang.Integer.MAX_VALUE
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/** A basic implementation of [SceneStage]. */
class SceneStageImpl
@Inject
internal constructor(
    private val engine: EngineCore,
    @Rendering private val renderingDispatcher: CoroutineDispatcher,
    @Rendering private val renderingClock: Clock,
    @Physics private val physicsDispatcher: CoroutineDispatcher,
) : SceneStage {

  // TODO: Add concurrency locks for concurrency safety if necessary.

  private val itemsPendingIntegration =
      MutableSharedFlow<DescendantAndAncestor>(replay = 0, extraBufferCapacity = MAX_VALUE)
  private val itemsPendingDisintegration =
      MutableSharedFlow<SceneItem>(replay = 0, extraBufferCapacity = MAX_VALUE)
  private val elementsPendingIntegration =
      MutableSharedFlow<ElementAndItem>(replay = 0, extraBufferCapacity = MAX_VALUE)
  private val elementsPendingDisintegration =
      MutableSharedFlow<ScenePrimitive>(replay = 0, extraBufferCapacity = MAX_VALUE)

  private val itemIntegrationProcessingStarted =
      MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false) }
  private val itemDisintegrationProcessingStarted =
      MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false) }
  private val elementIntegrationProcessingStarted =
      MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false) }
  private val elementDisintegrationProcessingStarted =
      MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false) }

  private val itemIntegrationJobs = mutableMapOf<SceneItem, Job>()
  private val elementIntegrationJobs = mutableMapOf<ScenePrimitive, Job>()

  private val rootItems = mutableSetOf<SceneItem>()
  private val itemsToAncestors = mutableMapOf<SceneItem, SceneItem>()
  private val elementsToItems = mutableMapOf<ScenePrimitive, SceneItem>()

  private val rootStaticPositioningNode =
      Node("root_static_positioning_node").also {
        runBlocking {
          withContext(renderingDispatcher) { engine.extractRootNode().attachChild(it) }
        }
      }
  private val staticPositioningNodes = mutableMapOf<SceneItem, Node>()
  private val dynamicPositioningNodes = mutableMapOf<ScenePrimitive, Node>()

  private val itemEnteredScene = MutableSharedFlow<SceneItem>(replay = 0)
  private val itemExitedScene = MutableSharedFlow<SceneItem>(replay = 0)
  private val elementEnteredScene = MutableSharedFlow<ScenePrimitive>(replay = 0)
  private val elementExitedScene = MutableSharedFlow<ScenePrimitive>(replay = 0)

  init {
    processItemsPendingIntegration()
    processItemsPendingDisintegration()
    processElementsPendingIntegration()
    processElementsPendingDisintegration()
  }

  override val allItems = elementsToItems.values.toSet()
  override val allElements = elementsToItems.keys.toSet()

  override suspend fun findItemByElement(element: ScenePrimitive) = elementsToItems[element]

  override suspend fun addItem(item: SceneItem) {
    waitForProcessingToStart()
    itemsPendingIntegration.emit(DescendantAndAncestor(item, ancestor = null))
  }

  override suspend fun removeItem(item: SceneItem) {
    waitForProcessingToStart()
    itemsPendingDisintegration.emit(item)
  }

  override suspend fun removeAllItems() {
    waitForProcessingToStart()
    // Use asFlow instead of iteration since the underlying structure will mutate during operation.
    rootItems.asFlow().onEach { itemsPendingDisintegration.emit(it) }.collect()
  }

  override fun elementEnteredScene() = elementEnteredScene

  override fun elementExitedScene() = elementExitedScene

  override fun itemEnteredScene() = itemEnteredScene

  override fun itemExitedScene() = itemExitedScene

  private suspend fun waitForProcessingToStart() =
      combine(
              itemIntegrationProcessingStarted,
              itemDisintegrationProcessingStarted,
              elementIntegrationProcessingStarted,
              elementDisintegrationProcessingStarted) { a, b, c, d ->
                a && b && c && d
              }
          .filter { it }
          .first()

  private fun processItemsPendingIntegration() {
    engine.extractCoroutineScope().launch {
      itemsPendingIntegration
          .onStart { itemIntegrationProcessingStarted.tryEmit(true) }
          .onEach {
            it.descendant.checkNotAttached()
            it.saveAncestorRelationship()
            it.connectStaticPositioningNodes()
            it.descendant.notifyEnterScene()
            itemIntegrationJobs[it.descendant] = launch {
              launch { it.descendant.queueAddedDescendantsForIntegration() }
              launch { it.descendant.queueRemovedDescendantsForDisintegration() }
              launch { it.descendant.queueAddedElementsForIntegration() }
              launch { it.descendant.queueRemovedElementsForDisintegration() }
              launch { it.descendant.syncItemPlacement() }
            }
          }
          .collect()
    }
  }

  private fun processItemsPendingDisintegration() {
    engine.extractCoroutineScope().launch {
      itemsPendingDisintegration
          .onStart { itemDisintegrationProcessingStarted.tryEmit(true) }
          .onEach {
            it.checkAttached()
            it.cancelIntegrationJob()
            it.queueCurrentDescendantsForDisintegration()
            it.queueCurrentElementsForDisintegration()
            it.disconnectStaticPositioningNodes()
            it.deleteAncestorRelationship()
            it.notifyExitScene()
          }
          .collect()
    }
  }

  private fun processElementsPendingIntegration() {
    engine.extractCoroutineScope().launch {
      elementsPendingIntegration
          .onStart { elementIntegrationProcessingStarted.tryEmit(true) }
          .onEach {
            it.saveContainerRelationship()
            it.element.attachToEngine()
            elementIntegrationJobs[it] = launch { launch { it.element.syncElementPlacement() } }
          }
          .collect()
    }
  }

  private fun processElementsPendingDisintegration() {
    engine.extractCoroutineScope().launch {
      elementsPendingDisintegration
          .onStart { elementDisintegrationProcessingStarted.tryEmit(true) }
          .onEach {
            it.detachFromEngine()
            it.cancelIntegrationJob()
            it.deleteContainerRelationship()
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

  private suspend fun DescendantAndAncestor.connectStaticPositioningNodes() =
      withContext(renderingDispatcher) {
        val ancestorNode = staticPositioningNodes[ancestor] ?: rootStaticPositioningNode
        Node("a")
            .apply { localTransform = descendant.placement().first().toJMonkeyTransform() }
            .also {
              staticPositioningNodes[descendant] = it
              ancestorNode.attachChild(it)
            }
      }

  private suspend fun SceneItem.disconnectStaticPositioningNodes() {
    val ancestorNode =
        itemsToAncestors[this]?.let { staticPositioningNodes[it] } ?: rootStaticPositioningNode
    val descendantNode = staticPositioningNodes[this]!!
    ancestorNode.detachChild(descendantNode)
    staticPositioningNodes.remove(this)
  }

  private suspend fun DescendantAndAncestor.saveAncestorRelationship() {
    if (ancestor == null) rootItems.add(descendant) else itemsToAncestors[descendant] = ancestor
  }

  private suspend fun SceneItem.deleteAncestorRelationship() {
    itemsToAncestors.remove(this)
  }

  private suspend fun ElementAndItem.saveContainerRelationship() {
    elementsToItems[element] = item
  }

  private suspend fun ScenePrimitive.deleteContainerRelationship() {
    elementsToItems.remove(this)
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
      descendantRemoved().onEach { itemsPendingDisintegration.tryEmit(it) }.collect()

  private suspend fun SceneItem.queueCurrentDescendantsForDisintegration() {
    descendants.forEach { itemsPendingDisintegration.tryEmit(it) }
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
    elements.forEach {
      elementsPendingDisintegration.tryEmit(ElementAndItem(it, elementsToItems[it]!!))
    }
  }

  private suspend fun ScenePrimitive.attachToEngine() {
    when (val element = this) {
      is Light -> withContext(renderingDispatcher) { engine.extractRootNode().addLight(element) }
      is ParticleEmitter ->
          withContext(renderingDispatcher) { engine.extractRootNode().attachChild(element) }
      is Spatial ->
          withContext(renderingDispatcher) { engine.extractRootNode().attachChild(element) }
      is PhysicsControl ->
          withContext(physicsDispatcher) {
            element.newNode().apply { addControl(element) }
            engine.extractPhysics().physicsSpace.add(element)
          }
      is PhysicsJoint ->
          withContext(physicsDispatcher) { engine.extractPhysics().physicsSpace.add(element) }
      else ->
          throw UnsupportedOperationException(
              "Cannot process $element. Type is currently unsupported.")
    }
  }

  private suspend fun ScenePrimitive.detachFromEngine() {

    when (val element = this) {
      is Light -> withContext(renderingDispatcher) { engine.extractRootNode().removeLight(element) }
      is ParticleEmitter ->
          withContext(renderingDispatcher) { engine.extractRootNode().detachChild(element) }
      is Spatial ->
          withContext(renderingDispatcher) { engine.extractRootNode().detachChild(element) }
      is PhysicsControl ->
          withContext(physicsDispatcher) {
            dynamicPositioningNodes[element]!!.removeControl(element)
            engine.extractPhysics().physicsSpace.remove(element)
          }
      is PhysicsJoint ->
          withContext(physicsDispatcher) { engine.extractPhysics().physicsSpace.remove(element) }
      else ->
          throw UnsupportedOperationException(
              "Cannot process $element. Type is currently unsupported.")
    }
  }

  private suspend fun ScenePrimitive.newNode() =
      Node("b").also {
        dynamicPositioningNodes[this] = it
        withContext(renderingDispatcher) { engine.extractRootNode().attachChild(it) }
      }

  private suspend fun SceneItem.syncItemPlacement() {
    engine.extractCoroutineScope().launch(renderingDispatcher) {
      val itemNode = staticPositioningNodes[this@syncItemPlacement]!!
      launch { itemNode.placement().distinctUntilChanged().onEach { placeAt(it) }.collect() }
      launch {
        placement()
            .distinctUntilChanged()
            .onEach { itemNode.setLocalTranslation(it.position.toJMonkeyVector()) }
            .collect()
      }
    }
  }

  // TODO check this is the right vector to use for quaternion to direction
  private suspend fun ScenePrimitive.syncElementPlacement() {
    engine.extractCoroutineScope().launch {
      val item = elementsToItems[this@syncElementPlacement]!!
      val itemNode = staticPositioningNodes[item]!!
      when (val element = this@syncElementPlacement) {
        is PointLight ->
            withContext(renderingDispatcher) {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .onEach { element.position = it.position.toJMonkeyVector() }
                  .collect()
            }
        is DirectionalLight ->
            withContext(renderingDispatcher) {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .map {
                    it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement())
                        .toJMonkeyTransform()
                  }
                  .onEach { itemNode.localTransform = it }
                  .collect()
            }
        is SpotLight ->
            withContext(renderingDispatcher) {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .onEach { element.position = it.position.toJMonkeyVector() }
                  .collect()
            }
        is ParticleEmitter ->
            withContext(renderingDispatcher) {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .onEach { element.localTransform = it.toJMonkeyTransform() }
                  .collect()
            }
        is Spatial ->
            withContext(renderingDispatcher) {
              itemNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .onEach { element.localTransform = it.toJMonkeyTransform() }
                  .collect()
            }
        is RigidBodyControl ->
            withContext(physicsDispatcher) {
              val physicsNode = dynamicPositioningNodes[element]!!
              launch {
                physicsNode
                    .absolutePlacement()
                    .distinctUntilChanged()
                    .map {
                      it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement())
                          .toJMonkeyTransform()
                    }
                    .flowOn(physicsDispatcher)
                    .onEach { itemNode.localTransform = it }
                    .flowOn(renderingDispatcher)
                    .collect()
              }
              launch {
                itemNode
                    .absolutePlacement()
                    .distinctUntilChanged()
                    .onEach {
                      element.physicsLocation = it.position.toJMonkeyVector()
                      element.physicsRotation = it.rotation.toJMonkeyQuaternion()
                    }
                    .collect()
              }
            }
        is GhostControl ->
            withContext(physicsDispatcher) {
              val physicsNode = dynamicPositioningNodes[element]!!
              launch {
                physicsNode
                    .absolutePlacement()
                    .distinctUntilChanged()
                    .map {
                      it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement())
                          .toJMonkeyTransform()
                    }
                    .flowOn(physicsDispatcher)
                    .onEach { itemNode.localTransform = it }
                    .flowOn(renderingDispatcher)
                    .collect()
              }
              launch {
                itemNode
                    .absolutePlacement()
                    .distinctUntilChanged()
                    .onEach {
                      element.physicsLocation = it.position.toJMonkeyVector()
                      element.physicsRotation = it.rotation.toJMonkeyQuaternion()
                    }
                    .collect()
              }
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

  private suspend fun Node.absolutePlacement() =
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
private data class DescendantAndAncestor(val descendant: SceneItem, val ancestor: SceneItem?)

/** A scene [element] and its enclosing [item]. */
private data class ElementAndItem(val element: ScenePrimitive, val item: SceneItem)
