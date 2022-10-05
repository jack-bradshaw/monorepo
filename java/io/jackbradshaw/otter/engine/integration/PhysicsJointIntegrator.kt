package io.jackbradshaw.otter.engine.integration

import com.jme3.bullet.joints.PhysicsJoint
import io.jackbradshaw.otter.engine.core.EngineCore

class PhysicsJointIntegrator(private val engineCore: EngineCore) : EngineIntegrator<PhysicsJoint> {

  override suspend fun integrate(element: PhysicsJoint) {
    engineCore.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: PhysicsJoint) {
    engineCore.extractPhysics().getPhysicsSpace().remove(element)
  }
}
