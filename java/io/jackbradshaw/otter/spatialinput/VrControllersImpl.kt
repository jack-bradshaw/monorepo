package io.jackbradshaw.otter.vr.controllers

import io.jackbradshaw.otter.math.toOtterPoint
import io.jackbradshaw.otter.math.toOtterQuaternion
import io.jackbradshaw.otter.physics.placement
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.Rendering
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import io.jackbradshaw.otter.physics.placeZero

class VrControllersImpl @Inject internal constructor(
    private val controllerFactory: VrControllerImplFactory

): VrControllers {

  private val controllers = ConcurrentHashMap<Int, VrController>()

  override suspend  fun getController(index: Int): VrController {
    return controllers.getOrPut(index) { controllerFactory.create(index) }
  }
}