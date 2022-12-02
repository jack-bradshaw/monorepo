package io.jackbradshaw.otter.scene.primitiveintegration

import com.jme3.light.Light
import io.jackbradshaw.otter.engine.core.EngineCore

class LightIntegrator(private val engineCore: EngineCore) : EngineIntegrator<Light> {

  override suspend fun integrate(element: Light) {
    engineCore.extractGameNode().addLight(element)
  }

  override suspend fun disintegrate(element: Light) {
    engineCore.extractGameNode().removeLight(element)
  }
}
