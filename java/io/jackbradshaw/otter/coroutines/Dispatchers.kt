package io.jackbradshaw.otter.coroutines

import com.jme3.app.Application
import com.jme3.bullet.PhysicsSpace
import io.jackbradshaw.otter.engine.core.EngineCore
<<<<<<< HEAD
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException
=======
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking

/**
 * Dispatcher for posting to the JMonkey Engine 3 [rendering update loop]
 * (https://wiki.jmonkeyengine.org/docs/3.4/core/app/update_loop.html).
 */
class JMonkeyRenderingDispatcher(private val application: Application) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    application.enqueue(block)
  }
}

/** Existing rendering dispatchers by engine. */
private val RENDERING_DISPATCHERS = ConcurrentHashMap<Application, JMonkeyRenderingDispatcher>()

/**
 * Gets a CoroutineDispatcher for this application. Every call for a given application returns the
 * same instance, and calls are thread safe.
 */
fun Application.renderingDispatcher(): CoroutineDispatcher =
    RENDERING_DISPATCHERS.getOrPut(this) { JMonkeyRenderingDispatcher(this) }

/**
 * Gets a CoroutineDispatcher for this application. Every call for a given application returns the
 * same instance, and calls are thread safe.
 */
fun EngineCore.renderingDispatcher(): CoroutineDispatcher =
    this.extractApplication().renderingDispatcher()

/**
 * Dispatcher for posting to the JMonkey Engine 3 [physics update loop]
 * (https://wiki.jmonkeyengine.org/docs/3.4/physics/collision/physics_listeners.html#how-to-listen-to-physics-ticks).
 */
class JMonkeyPhysicsDispatcher(private val physicsSpace: PhysicsSpace) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    runBlocking { physicsSpace.enqueue(block.toCallable()) }
  }
}

/** Existing physics dispatchers by engine. */
private val PHYSICS_DISPATCHERS = ConcurrentHashMap<PhysicsSpace, JMonkeyPhysicsDispatcher>()

fun PhysicsSpace.physicsDispatcher(): CoroutineDispatcher =
    PHYSICS_DISPATCHERS.getOrPut(this) { JMonkeyPhysicsDispatcher(this) }

fun EngineCore.physicsDispatcher(): CoroutineDispatcher =
    this.extractPhysics().getPhysicsSpace().physicsDispatcher()

private fun Runnable.toCallable() = { run() }
