package io.jackbradshaw.otter.engine.integration

import com.jme3.bullet.joints.PhysicsJoint
import io.jackbradshaw.otter.engine.Engine

class PhysicsJointIntegrator(private val engine: Engine) : EngineIntegrator<PhysicsJoint> {

  override suspend fun integrate(element: PhysicsJoint) {
    engine.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: PhysicsJoint) {
    engine.extractPhysics().getPhysicsSpace().remove(element)
  }
}