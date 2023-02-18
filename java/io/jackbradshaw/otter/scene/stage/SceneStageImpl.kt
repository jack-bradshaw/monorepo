package io.jackbradshaw.otter.scene.stage

import java.util.Queue
import java.util.LinkedList
import com.jme3.bullet.control.GhostControl
import com.jme3.bullet.control.PhysicsControl
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.bullet.joints.PhysicsJoint
import com.jme3.effect.ParticleEmitter
import com.jme3.light.DirectionalLight
import com.jme3.light.Light
import com.jme3.light.PointLight
import kotlinx.coroutines.flow.asFlow
import com.jme3.light.SpotLight
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import kotlinx.coroutines.flow.first
import com.jme3.scene.Spatial
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.math.model.*
<<<<<<< HEAD
import io.jackbradshaw.otter.physics.model.Placement
=======
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
import io.jackbradshaw.otter.physics.model.relativeTo
import io.jackbradshaw.otter.physics.model.toJMonkeyTransform
import io.jackbradshaw.otter.physics.model.toOtterPlacement
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.scene.item.SceneItem
import javax.inject.Inject
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
import kotlinx.coroutines.runBlocking
import java.lang.Integer.MAX_VALUE

/** A basic implementation of [SceneStage]. */
<<<<<<< HEAD
class SceneStageImpl @Inject internal constructor(
=======
class SceneStageImpl
@Inject
internal constructor(
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
    private val engine: EngineCore,
    @Rendering private val renderingDispatcher: CoroutineDispatcher,
    @Rendering private val renderingClock: Clock,
    @Physics private val physicsDispatcher: CoroutineDispatcher,
) : SceneStage {

  // TODO: Add concurrency locks for concurrency safety if necessary.

<<<<<<< HEAD
  private val itemsPendingIntegration = MutableSharedFlow<DescendantAndAncestor>(replay = 0, extraBufferCapacity = MAX_VALUE)
  private val itemsPendingDisintegration = MutableSharedFlow<SceneItem>(replay = 0, extraBufferCapacity = MAX_VALUE)
  private val elementsPendingIntegration = MutableSharedFlow<ElementAndItem>(replay = 0, extraBufferCapacity = MAX_VALUE)
  private val elementsPendingDisintegration = MutableSharedFlow<ScenePrimitive>(replay = 0, extraBufferCapacity = MAX_VALUE)

  private val itemIntegrationProcessingStarted = MutableSharedFlow<Boolean>(replay = 1).apply {
    tryEmit(false) }
  private val itemDisintegrationProcessingStarted = MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false)}
  private val elementIntegrationProcessingStarted = MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false)}
  private val elementDisintegrationProcessingStarted = MutableSharedFlow<Boolean>(replay = 1).apply { tryEmit(false)}
=======
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
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

  private val itemIntegrationJobs = mutableMapOf<SceneItem, Job>()
  private val elementIntegrationJobs = mutableMapOf<ScenePrimitive, Job>()

  private val rootItems = mutableSetOf<SceneItem>()
  private val itemsToAncestors = mutableMapOf<SceneItem, SceneItem>()
  private val elementsToItems = mutableMapOf<ScenePrimitive, SceneItem>()

<<<<<<< HEAD
  private val rootStaticPositioningNode = Node().also { runBlocking { withContext(renderingDispatcher) { engine.extractGameNode().attachChild(it) } }}
=======
  private val rootStaticPositioningNode =
      Node().also {
        runBlocking {
          withContext(renderingDispatcher) { engine.extractGameNode().attachChild(it) }
        }
      }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
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

<<<<<<< HEAD
  private suspend fun waitForProcessingToStart() = combine(
      itemIntegrationProcessingStarted,
      itemDisintegrationProcessingStarted,
      elementIntegrationProcessingStarted,
      elementDisintegrationProcessingStarted
  ) { a, b, c, d -> a && b && c && d }.filter { it }.first()
=======
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
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

  private fun processItemsPendingIntegration() {
    engine.extractCoroutineScope().launch(engine.extractApplication().renderingDispatcher()) {
      itemsPendingIntegration
<<<<<<< HEAD
          .onStart {
            itemIntegrationProcessingStarted.tryEmit(true) }
=======
          .onStart { itemIntegrationProcessingStarted.tryEmit(true) }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
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
              launch { it.descendant.syncPlacement() }
            }
          }
          .collect()
    }
  }

  private fun processItemsPendingDisintegration() {
    engine.extractCoroutineScope().launch {
      itemsPendingDisintegration
<<<<<<< HEAD
          .onStart {
            itemDisintegrationProcessingStarted.tryEmit(true) }
=======
          .onStart { itemDisintegrationProcessingStarted.tryEmit(true) }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
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
<<<<<<< HEAD
          .onStart {
            elementIntegrationProcessingStarted.tryEmit(true) }
          .onEach {
            it.saveContainerRelationship()
            it.element.attachToEngine()
            elementIntegrationJobs[it] = launch { launch {
              it.element.syncPlacement()
            }
            }
=======
          .onStart { elementIntegrationProcessingStarted.tryEmit(true) }
          .onEach {
            it.saveContainerRelationship()
            it.element.attachToEngine()
            elementIntegrationJobs[it] = launch { launch { it.element.syncPlacement() } }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
          }
          .collect()
    }
  }

  private fun processElementsPendingDisintegration() {
    engine.extractCoroutineScope().launch {
      elementsPendingDisintegration
<<<<<<< HEAD
          .onStart {
            elementDisintegrationProcessingStarted.tryEmit(true) }
=======
          .onStart { elementDisintegrationProcessingStarted.tryEmit(true) }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
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

<<<<<<< HEAD
  private suspend fun DescendantAndAncestor.connectStaticPositioningNodes() = withContext(renderingDispatcher){
    val ancestorNode = staticPositioningNodes[ancestor] ?: rootStaticPositioningNode
    val descendantNode = Node()
        .apply {
          val firstPlace = descendant.placement().first().position.toJMonkeyVector()
          println("first place $firstPlace")
          //setLocalTranslation(Vector3f(1f, 0f, 0f))
          setLocalTranslation(firstPlace)
        }
        .also { staticPositioningNodes[descendant] = it }
    ancestorNode.attachChild(descendantNode)
  }

  private suspend fun SceneItem.disconnectStaticPositioningNodes() {
    val ancestorNode = itemsToAncestors[this]?.let { staticPositioningNodes[it] } ?: rootStaticPositioningNode
=======
  private suspend fun DescendantAndAncestor.connectStaticPositioningNodes() =
      withContext(renderingDispatcher) {
        val ancestorNode = staticPositioningNodes[ancestor] ?: rootStaticPositioningNode
        val descendantNode =
            Node()
                .apply {
                  val firstPlace = descendant.placement().first().position.toJMonkeyVector()
                  println("first place $firstPlace")
                  // setLocalTranslation(Vector3f(1f, 0f, 0f))
                  setLocalTranslation(firstPlace)
                }
                .also { staticPositioningNodes[descendant] = it }
        ancestorNode.attachChild(descendantNode)
      }

  private suspend fun SceneItem.disconnectStaticPositioningNodes() {
    val ancestorNode =
        itemsToAncestors[this]?.let { staticPositioningNodes[it] } ?: rootStaticPositioningNode
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
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
<<<<<<< HEAD
      descendantRemoved()
          .onEach { itemsPendingDisintegration.tryEmit(it) }
          .collect()
=======
      descendantRemoved().onEach { itemsPendingDisintegration.tryEmit(it) }.collect()
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

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
<<<<<<< HEAD
    elements.forEach { elementsPendingDisintegration.tryEmit(ElementAndItem(it, elementsToItems[it]!!)) }
=======
    elements.forEach {
      elementsPendingDisintegration.tryEmit(ElementAndItem(it, elementsToItems[it]!!))
    }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
  }

  private suspend fun ScenePrimitive.attachToEngine() {
    when (val element = this) {
<<<<<<< HEAD
      is Light -> withContext(renderingDispatcher) {
            engine.extractGameNode().addLight(element)
      }

      is ParticleEmitter ->
        withContext(renderingDispatcher) {

          engine.extractGameNode().attachChild(element)
        }

      is Spatial -> withContext(renderingDispatcher) {

        engine.extractGameNode().attachChild(element)
      }

      is PhysicsControl ->
        withContext(physicsDispatcher) {

          element.newNode().apply { addControl(element) }
          engine.extractPhysics().physicsSpace.add(element)
        }

      is PhysicsJoint ->
        withContext(physicsDispatcher) {

          engine.extractPhysics().physicsSpace.add(element)
        }

=======
      is Light -> withContext(renderingDispatcher) { engine.extractGameNode().addLight(element) }
      is ParticleEmitter ->
          withContext(renderingDispatcher) { engine.extractGameNode().attachChild(element) }
      is Spatial ->
          withContext(renderingDispatcher) { engine.extractGameNode().attachChild(element) }
      is PhysicsControl ->
          withContext(physicsDispatcher) {
            element.newNode().apply { addControl(element) }
            engine.extractPhysics().physicsSpace.add(element)
          }
      is PhysicsJoint ->
          withContext(physicsDispatcher) { engine.extractPhysics().physicsSpace.add(element) }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
      else ->
        throw UnsupportedOperationException(
            "Cannot process $element. Type is currently unsupported.")
    }
  }

  private suspend fun ScenePrimitive.detachFromEngine() {

    when (val element = this) {
<<<<<<< HEAD
      is Light -> withContext(renderingDispatcher) {

        engine.extractGameNode().removeLight(element)
      }

      is ParticleEmitter ->
        withContext(renderingDispatcher) {

          engine.extractGameNode().detachChild(element)
        }

      is Spatial -> withContext(renderingDispatcher) {


        engine.extractGameNode().detachChild(element)
      }

      is PhysicsControl ->
        withContext(physicsDispatcher) {


          dynamicPositioningNodes[element]!!.removeControl(element)
          engine.extractPhysics().physicsSpace.remove(element)
        }

      is PhysicsJoint ->
        withContext(physicsDispatcher) {


          engine.extractPhysics().physicsSpace.remove(element)
        }

=======
      is Light -> withContext(renderingDispatcher) { engine.extractGameNode().removeLight(element) }
      is ParticleEmitter ->
          withContext(renderingDispatcher) { engine.extractGameNode().detachChild(element) }
      is Spatial ->
          withContext(renderingDispatcher) { engine.extractGameNode().detachChild(element) }
      is PhysicsControl ->
          withContext(physicsDispatcher) {
            dynamicPositioningNodes[element]!!.removeControl(element)
            engine.extractPhysics().physicsSpace.remove(element)
          }
      is PhysicsJoint ->
          withContext(physicsDispatcher) { engine.extractPhysics().physicsSpace.remove(element) }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
      else ->
        throw UnsupportedOperationException(
            "Cannot process $element. Type is currently unsupported.")
    }
  }

  private suspend fun ScenePrimitive.newNode() =
      Node().also {
        dynamicPositioningNodes[this] = it
<<<<<<< HEAD
        withContext(renderingDispatcher) {

          engine.extractGameNode().attachChild(it) }
      }

  private suspend fun SceneItem.syncPlacement() = withContext(renderingDispatcher) {
    val itemNode = staticPositioningNodes[this@syncPlacement]!!
    launch {
      itemNode
          .placement()
          .onEach { println("need to move item to ${it.position.x}") }
          .distinctUntilChanged()
          //.onEach { placeAt(it) }
          .collect()
    }
    launch {
      placement()
          .distinctUntilChanged()
          .onEach { println("need to move node to ${it.position.x}") }
          //.onEach { itemNode.localTransform = it.toJMonkeyTransform() }
          .collect()
    }
  }
=======
        withContext(renderingDispatcher) { engine.extractGameNode().attachChild(it) }
      }

  private suspend fun SceneItem.syncPlacement() =
      withContext(renderingDispatcher) {
        val itemNode = staticPositioningNodes[this@syncPlacement]!!
        launch {
          itemNode
              .placement()
              .onEach { println("need to move item to ${it.position.x}") }
              .distinctUntilChanged()
              // .onEach { placeAt(it) }
              .collect()
        }
        launch {
          placement()
              .distinctUntilChanged()
              .onEach { println("need to move node to ${it.position.x}") }
              // .onEach { itemNode.localTransform = it.toJMonkeyTransform() }
              .collect()
        }
      }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

  // TODO check this is the right vector to use for quaternion to direction
  private suspend fun ScenePrimitive.syncPlacement() {
    val item = elementsToItems[this]!!
    val itemNode = staticPositioningNodes[item]!!
    when (this) {
      is PointLight ->
<<<<<<< HEAD
        withContext(renderingDispatcher) {
          itemNode
              .absolutePlacement()
              .distinctUntilChanged()
              .onEach { position = it.position.toJMonkeyVector() }
              .collect()
        }

      is DirectionalLight ->
        withContext(renderingDispatcher) {
          itemNode
              .absolutePlacement()
              .distinctUntilChanged()
              .onEach { direction = unitXVector.rotateBy(it.rotation).toJMonkeyVector() }
              .collect()
        }

      is SpotLight ->
        withContext(renderingDispatcher) {
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
        withContext(renderingDispatcher) {
          itemNode
              .absolutePlacement()
              .distinctUntilChanged()
              .onEach { localTransform = it.toJMonkeyTransform() }
              .collect()
        }

      is Spatial ->
        withContext(renderingDispatcher) {
          itemNode
              .absolutePlacement()
              .distinctUntilChanged()
              .onEach { localTransform = it.toJMonkeyTransform() }
              .collect()
        }

      is RigidBodyControl ->
        withContext(physicsDispatcher) {
          val physicsNode = dynamicPositioningNodes[this@syncPlacement]!!
          launch {
            physicsNode
=======
          withContext(renderingDispatcher) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { position = it.position.toJMonkeyVector() }
                .collect()
          }
      is DirectionalLight ->
          withContext(renderingDispatcher) {
            itemNode
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
                .absolutePlacement()
                .distinctUntilChanged()
                .map { it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement()).toJMonkeyTransform() }
                .onEach { withContext(renderingDispatcher) { itemNode.localTransform = it } }
                .collect()
          }
<<<<<<< HEAD
          launch {
=======
      is SpotLight ->
          withContext(renderingDispatcher) {
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach {
                  this@syncPlacement.physicsLocation = it.position.toJMonkeyVector()
                  this@syncPlacement.physicsRotation = it.rotation.toJMonkeyQuaternion()
                }
                .collect()
          }
<<<<<<< HEAD
        }

      is GhostControl ->
        withContext(physicsDispatcher) {
          val physicsNode = dynamicPositioningNodes[this@syncPlacement]!!
          launch {
            physicsNode
                .absolutePlacement()
                .distinctUntilChanged()
                .map { it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement()).toJMonkeyTransform() }
                .onEach { withContext(renderingDispatcher) { itemNode.localTransform = it } }
                .collect()
=======
      is ParticleEmitter ->
          withContext(renderingDispatcher) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { localTransform = it.toJMonkeyTransform() }
                .collect()
          }
      is Spatial ->
          withContext(renderingDispatcher) {
            itemNode
                .absolutePlacement()
                .distinctUntilChanged()
                .onEach { localTransform = it.toJMonkeyTransform() }
                .collect()
          }
      is RigidBodyControl ->
          withContext(physicsDispatcher) {
            val physicsNode = dynamicPositioningNodes[this@syncPlacement]!!
            launch {
              physicsNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .map {
                    it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement())
                        .toJMonkeyTransform()
                  }
                  .onEach { withContext(renderingDispatcher) { itemNode.localTransform = it } }
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
          withContext(physicsDispatcher) {
            val physicsNode = dynamicPositioningNodes[this@syncPlacement]!!
            launch {
              physicsNode
                  .absolutePlacement()
                  .distinctUntilChanged()
                  .map {
                    it.relativeTo(itemNode.parent.worldTransform.toOtterPlacement())
                        .toJMonkeyTransform()
                  }
                  .onEach { withContext(renderingDispatcher) { itemNode.localTransform = it } }
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
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
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

  private suspend fun Node.absolutePlacement() =
      renderingClock
          .deltaSec()
          .map {
<<<<<<< HEAD
            //println("world translation ${getWorldTranslation()}")
=======
            // println("world translation ${getWorldTranslation()}")
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
            worldTransform
          }
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
