package io.matthewbradshaw.merovingian.physics

import io.matthewbradshaw.merovingian.engine.Engine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.ConcurrentHashMap
import com.jme3.bullet.collision.PhysicsCollisionListener
import com.jme3.bullet.collision.PhysicsCollisionEvent
import com.jme3.bullet.PhysicsSpace
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.channels.awaitClose

private val COLLISION_EVENTS = ConcurrentHashMap<PhysicsSpace, SharedFlow<PhysicsCollisionEvent>>()

fun Engine.collisionEvents() = COLLISION_EVENTS.getOrPut(extractPhysics().getPhysicsSpace()) {
  callbackFlow<PhysicsCollisionEvent> {
    val listener = object : PhysicsCollisionListener {
      override fun collision(event: PhysicsCollisionEvent) {
        trySend(event)
      }
    }
    extractPhysics().getPhysicsSpace().addCollisionListener(listener)
    awaitClose { extractPhysics().getPhysicsSpace().removeCollisionListener(listener) }
  }.shareIn(extractCoroutineScope(), SharingStarted.Eagerly, replay = 0)
}