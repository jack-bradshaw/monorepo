package io.jackbradshaw.otter.scene.integration

import com.jme3.bullet.control.BetterCharacterControl
import io.jackbradshaw.otter.engine.core.EngineCore

class PhysicsCharacterIntegrator(private val engineCore: EngineCore) :
    EngineIntegrator<BetterCharacterControl> {

  override suspend fun integrate(element: BetterCharacterControl) {
    engineCore.extractPhysics().getPhysicsSpace().add(element)
  }

  override suspend fun disintegrate(element: BetterCharacterControl) {
    engineCore.extractPhysics().getPhysicsSpace().remove(element)
  }
}
