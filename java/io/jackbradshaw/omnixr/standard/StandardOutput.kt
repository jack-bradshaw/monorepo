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

