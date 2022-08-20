package io.jackbradshaw.otter.engine.integrators

import io.jackbradshaw.otter.engine.Engine
import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.bullet.joints.PhysicsJoint

class PhysicsJointIntegrator(
    private val engine: Engine
) : Integrator<PhysicsJoint> {

  override suspend fun integrate(element: PhysicsJoint) {
    engine.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: PhysicsJoint) {
    engine.extractPhysics().getPhysicsSpace().remove(element)
  }
}
