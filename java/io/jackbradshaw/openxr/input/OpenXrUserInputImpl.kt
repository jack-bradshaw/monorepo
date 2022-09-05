package io.jackbradshaw.otter.openxr.input

import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Identifier
import io.jackbradshaw.otter.openxr.model.Location
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.Rendering
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.physics.Placement
import io.jackbradshaw.otter.math.Vector
import kotlinx.coroutines.flow.Flow
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided

@AutoFactory
class OpenXrUserInputImpl(
    @Provided @Rendering private val clock: Clock,
    @Provided private val engine: Engine,
    //@Provided private val config: OpenXrConfig,
    private val user: User,
    private val identifer: Identifier,
    private val location: Location,
    private val profile: InteractionProfile
) : OpenXrUserInput {

  private val openXrBridge = engine.extractXr()?.getVRinput() ?: throw IllegalStateException(
      "The engine is not XR enabled, cannot initialize OpenXrHidImpl."
  )

  override fun clicked(): Flow<Boolean> {
    TODO()
  }

  override fun touched(): Flow<Boolean> {
    TODO()
  }

  override fun forced(): Flow<Float> {
    TODO()
  }

  override fun pulled(): Flow<Float> {
    TODO()
  }

  override fun pushed(): Flow<Vector> {
    TODO()
  }

  override fun twisted(): Flow<Float> {
    TODO()
  }

  override fun posed(): Flow<Placement> {
    TODO()
  }
}


/*

// The flow needs to be updated each tick so it is expensive to maintain. To save resources, share a single flow.
  private val sharedPlacementFlow by lazy {
    clock.deltaSec().map {
      val count = vrInput.getTrackedControllerCount()
      if (controllerIndex > count - 1 && vrInput.isInputDeviceTracking(controllerIndex)) {
        placeZero
      } else {
        val position = vrInput.getFinalObserverPosition(controllerIndex)
        val rotation = vrInput.getFinalObserverRotation(controllerIndex)
        placement(position = position.toOtterPoint(), rotation = rotation.toOtterQuaternion())
      }
    }.shareIn(engine.extractCoroutineScope(), SharingStarted.Lazily)
  }
 */