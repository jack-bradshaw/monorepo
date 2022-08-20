package io.jackbradshaw.otter.physics.collisions

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.collision.PhysicsCollisionEvent
import com.jme3.bullet.collision.PhysicsCollisionListener
import io.jackbradshaw.otter.engine.Engine
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.shareIn
import java.util.concurrent.ConcurrentHashMap

private val COLLISION_EVENTS = ConcurrentHashMap<PhysicsSpace, SharedFlow<Collision>>()

fun Engine.allCollisions(): Flow<Collision> =
  COLLISION_EVENTS.getOrPut(extractPhysics().getPhysicsSpace()) {
    newCollisionEventFlow()
  }

private fun Engine.newCollisionEventFlow() = callbackFlow<Collision> {
  val listener = object : PhysicsCollisionListener {
    override fun collision(event: PhysicsCollisionEvent) {
      trySend(event.toCollision())
    }
  }
  extractPhysics().getPhysicsSpace().addCollisionListener(listener)
  awaitClose { extractPhysics().getPhysicsSpace().removeCollisionListener(listener) }
}.shareIn(extractCoroutineScope(), SharingStarted.Lazily, replay = 0)