package io.jackbradshaw.otter.engine.integrators

import io.jackbradshaw.otter.engine.Engine
import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.bullet.control.BetterCharacterControl
import com.jme3.post.SceneProcessor

class SceneProcessorIntegrator(
    private val engine: Engine
) : Integrator<SceneProcessor> {

  override suspend fun integrate(element: SceneProcessor) {
    engine.extractViewPort().addProcessor(element)
  }

  override suspend fun disintegrate(element: SceneProcessor) {
    engine.extractViewPort().removeProcessor(element)
  }
}
