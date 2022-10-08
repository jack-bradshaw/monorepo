package io.jackbradshaw.otter.scene.integration

import com.jme3.bullet.collision.PhysicsCollisionObject
import io.jackbradshaw.otter.engine.core.EngineCore

class PhysicsColliderIntegrator(private val engineCore: EngineCore) :
    EngineIntegrator<PhysicsCollisionObject> {

  override suspend fun integrate(element: PhysicsCollisionObject) {
    engineCore.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: PhysicsCollisionObject) {
    engineCore.extractPhysics().getPhysicsSpace().remove(element)
  }
}
