package java.io.jackbradshaw.openxr.standard

import io.jackbradshaw.otter.openxr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.otter.openxr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.otter.openxr.standard.StandardUser.GAMEPAD
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.KHRONOS_SIMPLE_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.HTC_VIVE_PRO
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.HTC_VIVE_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.MICROSOFT_XBOX_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.OCCULUS_TOUCH_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.OCCULUS_GO_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.VALVE_INDEX_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardUser.HEAD
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.SELECT
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.MENU
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.SYSTEM
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.SQUEEZE
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.TRIGGER
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.VIEW
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.BACK
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.DPAD_DOWN
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.DPAD_UP
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.DPAD_LEFT
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.DPAD_RIGHT
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.A
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.B
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.SHOULDER
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent.TOUCH
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.TRACKPAD
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.THUMBSTICK
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.THUMBREST
import io.jackbradshaw.otter.openxr.standard.StandardOutputIdentifier.HAPTIC
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.GRIP
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.AIM
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.VOLUME_UP
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.VOLUME_DOWN
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier.MUTE_MIC
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent.CLICK
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent.POSE
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent.FORCE
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent.VALUE
import io.jackbradshaw.otter.openxr.standard.StandardOutputLocation.LEFT_TRIGGER
import io.jackbradshaw.otter.openxr.standard.StandardOutputLocation.RIGHT_TRIGGER
import io.jackbradshaw.otter.openxr.standard.StandardOutputLocation.LEFT
import io.jackbradshaw.otter.openxr.standard.StandardOutputLocation.RIGHT
import io.jackbradshaw.otter.openxr.model.input
import io.jackbradshaw.otter.openxr.model.output
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.Output
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import io.jackbradshaw.otter.openxr.standard.StandardOutputIdentifier
import io.jackbradshaw.otter.openxr.standard.StandardOutputLocation
import io.jackbradshaw.otter.openxr.standard.StandardUser

private fun output(
    interactionProfile: StandardInteractionProfile,
    user: StandardUser,
    identifier: StandardOutputIdentifier,
    location: StandardOutputLocation? = null
): Output = output(interactionProfile.interactionProfile, user.user, identifier.identifier, location?.location)

enum class StandardOutput(val output: Output) {
  KHRONOS_SIMPLE_LEFT_HAPTIC(output(KHRONOS_SIMPLE_CONTROLLER, LEFT_HAND, HAPTIC)),
  KHRONOS_SIMPLE_RIGHT_HAPTIC(output(KHRONOS_SIMPLE_CONTROLLER, RIGHT_HAND, HAPTIC)),
  HTC_VIVE_CONTROLLER_LEFT_HAPTIC(output(HTC_VIVE_CONTROLLER, LEFT_HAND, HAPTIC)),
  HTC_VIVE_CONTROLLER_RIGHT_HAPTIC(output(HTC_VIVE_CONTROLLER, RIGHT_HAND, HAPTIC)),
  MICROSOFT_MR_CONTROLLER_LEFT_HAPTIC(output(MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER, LEFT_HAND, HAPTIC)),
  MICROSOFT_MR_CONTROLLER_RIGHT_HAPTIC(output(MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER, RIGHT_HAND, HAPTIC)),
  MICROSOFT_XBOX_CONTROLLER_GAMEPAD_HAPTIC_LEFT(
      output(MICROSOFT_XBOX_CONTROLLER, GAMEPAD, HAPTIC, LEFT)
  ),
  MICROSOFT_XBOX_CONTROLLER_GAMEPAD_HAPTIC_RIGHT(
      output(MICROSOFT_XBOX_CONTROLLER, GAMEPAD, HAPTIC, RIGHT)
  ),
  MICROSOFT_XBOX_CONTROLLER_GAMEPAD_HAPTIC_LEFT_TRIGGER(
      output(MICROSOFT_XBOX_CONTROLLER, GAMEPAD, HAPTIC,LEFT_TRIGGER)
  ),
  MICROSOFT_XBOX_CONTROLLER_GAMEPAD_HAPTIC_RIGHT_TRIGGER(
      output(MICROSOFT_XBOX_CONTROLLER, GAMEPAD, HAPTIC, RIGHT_TRIGGER)
  ),
  OCULUS_TOUCH_LEFT_HAPTIC(output(OCCULUS_TOUCH_CONTROLLER, LEFT_HAND, HAPTIC)),
  OCULUS_TOUCH_RIGHT_HAPTIC(output(OCCULUS_TOUCH_CONTROLLER, RIGHT_HAND, HAPTIC)),
  VALVE_INDEX_LEFT_HAPTIC(output(VALVE_INDEX_CONTROLLER, LEFT_HAND, HAPTIC)),
  VALVE_INDEX_RIGHT_HAPTIC(output(VALVE_INDEX_CONTROLLER, RIGHT_HAND, HAPTIC)),
}