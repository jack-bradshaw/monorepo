package io.jackbradshaw.otter.openxr.output

import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.Rendering
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Identifier
import io.jackbradshaw.otter.openxr.model.Location
import io.jackbradshaw.otter.physics.Placement
import io.jackbradshaw.otter.math.Vector
import kotlinx.coroutines.flow.Flow
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided

@AutoFactory
class OpenXrUserOutputImpl(
    @Provided @Rendering private val clock: Clock,
    @Provided private val engine: Engine,
    //@Provided private val config: OpenXrConfig,
    private val user: User
) : OpenXrUserOutput {
  override suspend fun engageHapticFeedback(intensityProportion: Float) = TODO()
  override suspend fun disengageHapticFeedback() = TODO()
}