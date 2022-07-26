package io.matthewbradshaw.jockstrap.physics

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.collision.PhysicsCollisionEvent
import com.jme3.bullet.collision.PhysicsCollisionListener
import io.matthewbradshaw.jockstrap.engine.Engine
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.shareIn
import java.util.concurrent.ConcurrentHashMap

private val COLLISION_EVENTS = ConcurrentHashMap<PhysicsSpace, SharedFlow<PhysicsCollisionEvent>>()

fun Engine.collisionEvents(): Flow<PhysicsCollisionEvent> =
  COLLISION_EVENTS.getOrPut(extractPhysics().getPhysicsSpace()) {
    newCollisionEventFlow()
  }

private fun Engine.newCollisionEventFlow() = callbackFlow<PhysicsCollisionEvent> {
  val listener = object : PhysicsCollisionListener {
    override fun collision(event: PhysicsCollisionEvent) {
      trySend(event)
    }
  }
  extractPhysics().getPhysicsSpace().addCollisionListener(listener)
  awaitClose { extractPhysics().getPhysicsSpace().removeCollisionListener(listener) }
}.shareIn(extractCoroutineScope(), SharingStarted.Lazily, replay = 0)