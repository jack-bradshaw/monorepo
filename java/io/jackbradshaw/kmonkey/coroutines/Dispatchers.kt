package io.jackbradshaw.kmonkey.coroutines

import com.jme3.app.Application
import com.jme3.bullet.PhysicsSpace
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher


/** A [CoroutineDispatcher] that posts to the rendering thread of [application]. */
class JMonkeyRenderingDispatcher(private val application: Application) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    application.enqueue(block)
  }
}

/** A [CoroutineDispatcher] that posts to the physics thread of [physicsSpace]. */
class JMonkeyPhysicsDispatcher(private val physicsSpace: PhysicsSpace) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    physicsSpace.enqueue(block.toCallable())
  }
}

/**
 * A cache of rendering dispatchers, keyed by [Application]. To ensure the next call to [renderingDispatcher] returns
 * a new dispatcher, remove the map entry for the application from here.
 */
val RENDERING_DISPATCHERS = ConcurrentHashMap<Application, JMonkeyRenderingDispatcher>()

/**
 * A cache of physics dispatchers, keyed by [PhysicsSpace]. To ensure the next call to [physicsDispatcher] returns
 * a new dispatcher, remove the map entry for the physics space from here.
 */
private val PHYSICS_DISPATCHERS = ConcurrentHashMap<PhysicsSpace, JMonkeyPhysicsDispatcher>()

/**
 * Gets a [CoroutineDispatcher] that executes coroutines on the rendering thread of this [Application].
 *
 * If a dispatcher already exists for this application then the existing instance is returned,
 * otherwise a new one is created. Calls to this function are always thread safe.
 */
fun Application.renderingDispatcher(): CoroutineDispatcher =
    RENDERING_DISPATCHERS.getOrPut(this) { JMonkeyRenderingDispatcher(this) }

/**
 * Gets a [CoroutineDispatcher] that executes coroutines on the physics thread of this [PhysicsSpace].
 *
 * If a dispatcher already exists for this physics space then the existing instance is returned,
 * otherwise a new one is created. Calls to this function are always thread safe.
 */
fun PhysicsSpace.physicsDispatcher(): CoroutineDispatcher =
    PHYSICS_DISPATCHERS.getOrPut(this) { JMonkeyPhysicsDispatcher(this) }

private fun Runnable.toCallable() = { run() }
