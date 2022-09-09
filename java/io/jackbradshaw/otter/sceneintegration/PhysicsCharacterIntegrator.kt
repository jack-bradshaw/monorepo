package io.jackbradshaw.otter.engine.sceneintegration

import io.jackbradshaw.otter.engine.Engine
import com.jme3.bullet.control.BetterCharacterControl

class PhysicsCharacterIntegrator(
    private val engine: Engine
) : SceneIntegrator<BetterCharacterControl> {

  override suspend fun integrate(element: BetterCharacterControl) {
    engine.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: BetterCharacterControl) {
    engine.extractPhysics().getPhysicsSpace().remove(element)
  }
}
