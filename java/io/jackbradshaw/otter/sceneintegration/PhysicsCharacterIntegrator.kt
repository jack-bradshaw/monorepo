package io.jackbradshaw.otter.engine.sceneintegration

import com.jme3.bullet.control.BetterCharacterControl
import io.jackbradshaw.otter.engine.Engine

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
