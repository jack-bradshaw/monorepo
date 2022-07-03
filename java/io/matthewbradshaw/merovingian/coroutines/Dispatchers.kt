package io.matthewbradshaw.merovingian.coroutines

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.PhysicsTickListener
import com.jme3.bullet.control.GhostControl
import com.jme3.scene.Node
import io.matthewbradshaw.klu.collections.SimpleDoubleListBuffer
import io.matthewbradshaw.merovingian.engine.Engine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

/**
 * Dispatcher for posting to the JMonkey Engine 3
 * [rendering loop](https://wiki.jmonkeyengine.org/docs/3.4/core/app/update_loop.html).
 */
class JMonkeyRenderingDispatcher(private val engine: Engine) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    engine.extractApp().enqueue(block)
  }
}

/**
 * Dispatcher for posting to the JMonkey Engine 3 physics
 * [pre-tick loop](https://wiki.jmonkeyengine.org/docs/3.4/physics/collision/physics_listeners.html#how-to-listen-to-physics-ticks).
 */
class JMonkeyPhysicsPreTickDispatcher(private val engine: Engine) : CoroutineDispatcher() {

  private val pendingRunnables = SimpleDoubleListBuffer<Runnable>()

  init {
    val ghostControl = object : GhostControl(), PhysicsTickListener {
      override fun prePhysicsTick(space: PhysicsSpace, tpf: Float) = runBlocking {
        pendingRunnables.switch()
        pendingRunnables.getInactive().forEach { it.run() }
      }

      override fun physicsTick(space: PhysicsSpace, tpf: Float) { /* unused */
      }
    }
    val ghost = Node("physics_pre_tick_dispatcher_ghost").apply { addControl(ghostControl) }
    engine.extractFrameworkNode().attachChild(ghost)
    engine.extractPhysics().getPhysicsSpace().add(ghostControl)
  }

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    runBlocking {
      pendingRunnables.getActive().add(block)
    }
  }
}

/**
 * Dispatcher for posting to the JMonkey Engine 3 physics
 * [on-tick loop](https://wiki.jmonkeyengine.org/docs/3.4/physics/collision/physics_listeners.html#how-to-listen-to-physics-ticks).
 */
class JMonkeyPhysicsOnTickDispatcher(private val engine: Engine) : CoroutineDispatcher() {
  private val pendingRunnables = SimpleDoubleListBuffer<Runnable>()

  init {
    val ghostControl = object : GhostControl(), PhysicsTickListener {
      override fun physicsTick(space: PhysicsSpace, tpf: Float) = runBlocking {
        pendingRunnables.switch()
        pendingRunnables.getInactive().forEach { it.run() }
      }

      override fun prePhysicsTick(space: PhysicsSpace, tpf: Float) { /* unused */
      }
    }
    val ghost = Node("physics_pre_tick_dispatcher_ghost").apply { addControl(ghostControl) }
    engine.extractFrameworkNode().attachChild(ghost)
    engine.extractPhysics().getPhysicsSpace().add(ghostControl)
  }

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    runBlocking {
      pendingRunnables.getActive().add(block)
    }
  }
}

/**
 * Map from application to dispatcher. Ensures safe concurrency and exactly one dispatcher per application.
 */
private val RENDERING_DISPATCHERS = ConcurrentHashMap<Engine, JMonkeyRenderingDispatcher>()

/**
 * Map from application to dispatcher. Ensures safe concurrency and exactly one dispatcher per application.
 */
private val PHYSICS_PRE_TICK_DISPATCHERS = ConcurrentHashMap<Engine, JMonkeyPhysicsPreTickDispatcher>()

/**
 * Map from application to dispatcher. Ensures safe concurrency and exactly one dispatcher per application.
 */
private val PHYSICS_ON_TICK_DISPATCHERS = ConcurrentHashMap<Engine, JMonkeyPhysicsOnTickDispatcher>()

/**
 * Gets a CoroutineDispatcher for this application. Every call for a given application returns the same instance, and
 * calls are thread safe.
 */
fun Engine.renderingDispatcher(): CoroutineDispatcher =
  RENDERING_DISPATCHERS.getOrPut(this) { JMonkeyRenderingDispatcher(this) }

/**
 */
fun Engine.physicsPreTickDispatcher(): CoroutineDispatcher =
  PHYSICS_PRE_TICK_DISPATCHERS.getOrPut(this) { JMonkeyPhysicsPreTickDispatcher(this) }

/**

 */
fun Engine.physicsOnTickDispatcher(): CoroutineDispatcher =
  PHYSICS_ON_TICK_DISPATCHERS.getOrPut(this) { JMonkeyPhysicsOnTickDispatcher(this) }
