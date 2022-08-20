package io.jackbradshaw.otter.engine.integrators

import io.jackbradshaw.otter.engine.Engine
import com.jme3.scene.Spatial

class SpatialIntegrator(
    private val engine: Engine
) : Integrator<Spatial> {

  override suspend fun integrate(element: Spatial) {
    engine.extractGameNode().attachChild(element)
  }

  override suspend fun disintegrate(element: Spatial) {
    engine.extractGameNode().detachChild(element)
  }
}
