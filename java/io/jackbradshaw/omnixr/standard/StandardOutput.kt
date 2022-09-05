package java.io.jackbradshaw.omnixr.standard

import io.jackbradshaw.omnixr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.omnixr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.omnixr.standard.StandardUser.GAMEPAD
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.KHRONOS_SIMPLE_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.HTC_VIVE_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.MICROSOFT_XBOX_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.OCCULUS_TOUCH_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.VALVE_INDEX_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardOutputIdentifier.HAPTIC
import io.jackbradshaw.omnixr.standard.StandardOutputLocation.LEFT_TRIGGER
import io.jackbradshaw.omnixr.standard.StandardOutputLocation.RIGHT_TRIGGER
import io.jackbradshaw.omnixr.standard.StandardOutputLocation.LEFT
import io.jackbradshaw.omnixr.standard.StandardOutputLocation.RIGHT
import io.jackbradshaw.omnixr.model.output
import io.jackbradshaw.omnixr.model.Output
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile
import io.jackbradshaw.omnixr.standard.StandardOutputIdentifier
import io.jackbradshaw.omnixr.standard.StandardOutputLocation
import io.jackbradshaw.omnixr.standard.StandardUser

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