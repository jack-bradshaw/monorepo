package io.jackbradshaw.otter.scene.primitiveintegration

import com.jme3.post.SceneProcessor
import io.jackbradshaw.otter.engine.core.EngineCore

class SceneProcessorIntegrator(private val engineCore: EngineCore) : EngineIntegrator<SceneProcessor> {

  override suspend fun integrate(element: SceneProcessor) {
    engineCore.extractDefaultViewPort().addProcessor(element)
  }

  override suspend fun disintegrate(element: SceneProcessor) {
    engineCore.extractDefaultViewPort().removeProcessor(element)
  }
}
