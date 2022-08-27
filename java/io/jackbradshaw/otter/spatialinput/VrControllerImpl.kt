package io.jackbradshaw.otter.vr.controllers

import io.jackbradshaw.otter.math.toOtterPoint
import io.jackbradshaw.otter.math.toOtterQuaternion
import io.jackbradshaw.otter.physics.placement
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.Rendering
import kotlinx.coroutines.flow.map
import io.jackbradshaw.otter.math.Vector
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject
import io.jackbradshaw.otter.physics.placeZero

@AutoFactory
class VrControllerImpl @Inject internal constructor(
    @Provided @Rendering private val clock: Clock,
    @Provided private val engine: Engine,
    private val controllerIndex: Int
) : VrController {

  private val vrInput = engine.extractVr()?.getVRinput() ?: throw IllegalStateException(
      "The engine is not VR enabled, cannot instantiate VrControllerImpl."
  )

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

  private val hapticsOn = MutableStateFlow(false)

  override fun placement() = sharedPlacementFlow

  override suspend fun setHapticFeedbackOn(on: Boolean) {
    hapticsOn.value = on
    vrInput.triggerHapticPulse(controllerIndex, if (on) Float.MAX_VALUE else 0f)
  }

  override suspend fun isHapticFeedbackOn() = hapticsOn
  override fun buttonPressed(): Flow<Boolean> = TODO()
  override fun gripped(): Flow<Boolean> = TODO()
  override fun triggered(): Flow<Boolean> = TODO()
  override fun thumbstickPressed(): Flow<Boolean> = TODO()
  override fun thumbstickPosition(): Flow<Vector> = TODO()
}