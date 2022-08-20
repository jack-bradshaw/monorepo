package io.jackbradshaw.otter.engine.integrators

import io.jackbradshaw.otter.engine.Engine
import com.jme3.bullet.collision.PhysicsCollisionObject

class PhysicsColliderIntegrator(
    private val engine: Engine
) : Integrator<PhysicsCollisionObject> {

  override suspend fun integrate(element: PhysicsCollisionObject) {
    engine.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: PhysicsCollisionObject) {
    engine.extractPhysics().getPhysicsSpace().remove(element)
  }
}
