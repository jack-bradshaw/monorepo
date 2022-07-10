package io.matthewbradshaw.frankl.coroutines

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.PhysicsTickListener
import com.jme3.bullet.control.GhostControl
import com.jme3.scene.Node
import io.matthewbradshaw.klu.collections.SimpleDoubleListBuffer
import io.matthewbradshaw.frankl.engine.Engine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

/**
 * Dispatcher for posting to the JMonkey Engine 3 [rendering update loop]
 * (https://wiki.jmonkeyengine.org/docs/3.4/core/app/update_loop.html).
 */
class JMonkeyRenderingDispatcher(private val engine: Engine) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    engine.extractApp().enqueue(block)
  }
}

/**
 * Dispatcher for posting to the JMonkey Engine 3 [physics update loop]
 * (https://wiki.jmonkeyengine.org/docs/3.4/physics/collision/physics_listeners.html#how-to-listen-to-physics-ticks).
 */
class JMonkeyPhysicsDispatcher(private val engine: Engine) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    runBlocking {
      engine.extractPhysics().getPhysicsSpace().enqueue(block.toCallable())
    }
  }
}

/**
 * Existing rendering dispatchers by engine.
 */
private val RENDERING_DISPATCHERS = ConcurrentHashMap<Engine, JMonkeyRenderingDispatcher>()

/**
 * Existing physics dispatchers by engine.
 */
private val PHYSICS_DISPATCHERS = ConcurrentHashMap<Engine, JMonkeyPhysicsDispatcher>()

/**
 * Gets a CoroutineDispatcher for this application. Every call for a given application returns the same instance, and
 * calls are thread safe.
 */
fun Engine.renderingDispatcher(): CoroutineDispatcher =
  RENDERING_DISPATCHERS.getOrPut(this) { JMonkeyRenderingDispatcher(this) }

/**
 */
fun Engine.physicsDispatcher(): CoroutineDispatcher =
  PHYSICS_DISPATCHERS.getOrPut(this) { JMonkeyPhysicsDispatcher(this) }

private fun Runnable.toCallable() = { run() }