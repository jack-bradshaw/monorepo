package io.jackbradshaw.otter.scene.integration

import com.jme3.scene.Spatial
import io.jackbradshaw.otter.engine.core.EngineCore

class SpatialIntegrator(private val engineCore: EngineCore) : EngineIntegrator<Spatial> {

  override suspend fun integrate(element: Spatial) {
    engineCore.extractGameNode().attachChild(element)
  }

  override suspend fun disintegrate(element: Spatial) {
    engineCore.extractGameNode().detachChild(element)
  }
}
