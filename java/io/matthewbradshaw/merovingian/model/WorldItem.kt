package io.matthewbradshaw.merovingian.model

import com.jme3.scene.Spatial
import com.jme3.bullet.collision.PhysicsCollisionObject

interface WorldItem {
  suspend fun visual(): Spatial
  suspend fun logical() = Unit
  suspend fun physical(): PhysicsCollisionObject? = null
}