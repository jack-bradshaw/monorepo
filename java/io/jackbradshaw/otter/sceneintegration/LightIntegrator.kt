package io.jackbradshaw.otter.engine.sceneintegration

import com.jme3.light.Light
import io.jackbradshaw.otter.engine.Engine

class LightIntegrator(private val engine: Engine) : SceneIntegrator<Light> {

  override suspend fun integrate(element: Light) {
    engine.extractGameNode().addLight(element)
  }

  override suspend fun disintegrate(element: Light) {
    engine.extractGameNode().removeLight(element)
  }
}
