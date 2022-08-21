package io.jackbradshaw.otter.engine.integrators

import io.jackbradshaw.otter.engine.Engine
import com.jme3.scene.Node

class NodeIntegrator(
    private val engine: Engine
) : Integrator<Node> {

  override suspend fun integrate(element: Node) {
    engine.extractGameNode().attachChild(element)
  }

  override suspend fun disintegrate(element: Node) {
    engine.extractGameNode().detachChild(element)
  }
}
