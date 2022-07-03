package io.matthewbradshaw.merovingian.model

import com.jme3.scene.Spatial
import com.jme3.bullet.collision.PhysicsCollisionObject

interface WorldItem {
  suspend fun representation(): Spatial
  suspend fun logic() = Unit
  suspend fun physics(): PhysicsCollisionObject? = null
}