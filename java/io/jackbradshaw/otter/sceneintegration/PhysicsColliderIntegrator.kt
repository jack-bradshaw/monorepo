package io.jackbradshaw.otter.engine.sceneintegration

import com.jme3.bullet.collision.PhysicsCollisionObject
import io.jackbradshaw.otter.engine.Engine

class PhysicsColliderIntegrator(
    private val engine: Engine
) : SceneIntegrator<PhysicsCollisionObject> {

  override suspend fun integrate(element: PhysicsCollisionObject) {
    engine.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: PhysicsCollisionObject) {
    engine.extractPhysics().getPhysicsSpace().remove(element)
  }
}
