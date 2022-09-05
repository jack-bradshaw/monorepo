package io.jackbradshaw.omnixr.standard

import io.jackbradshaw.omnixr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.omnixr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.omnixr.standard.StandardUser.GAMEPAD
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.KHRONOS_SIMPLE_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.HTC_VIVE_PRO
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.HTC_VIVE_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.MICROSOFT_XBOX_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.OCCULUS_TOUCH_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.OCCULUS_GO_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile.VALVE_INDEX_CONTROLLER
import io.jackbradshaw.omnixr.standard.StandardUser.HEAD
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.SELECT
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.MENU
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.SYSTEM
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.SQUEEZE
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.TRIGGER
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.VIEW
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.BACK
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.DPAD_DOWN
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.DPAD_UP
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.DPAD_LEFT
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.DPAD_RIGHT
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.A
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.B
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.SHOULDER
import io.jackbradshaw.omnixr.standard.StandardInputLocation.LEFT
import io.jackbradshaw.omnixr.standard.StandardInputLocation.RIGHT
import io.jackbradshaw.omnixr.standard.StandardInputComponent.TOUCH
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.TRACKPAD
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.THUMBSTICK
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.THUMBREST
import io.jackbradshaw.omnixr.standard.StandardOutputIdentifier.HAPTIC
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.GRIP
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.AIM
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.VOLUME_UP
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.VOLUME_DOWN
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier.MUTE_MIC
import io.jackbradshaw.omnixr.standard.StandardInputComponent.CLICK
import io.jackbradshaw.omnixr.standard.StandardInputComponent.POSE
import io.jackbradshaw.omnixr.standard.StandardInputComponent.FORCE
import io.jackbradshaw.omnixr.standard.StandardInputComponent.VALUE
import io.jackbradshaw.omnixr.standard.StandardOutputLocation.LEFT_TRIGGER
import io.jackbradshaw.omnixr.standard.StandardOutputLocation.RIGHT_TRIGGER
import io.jackbradshaw.omnixr.model.input
import io.jackbradshaw.omnixr.model.output
import io.jackbradshaw.omnixr.model.Input
import io.jackbradshaw.omnixr.model.Output

private fun input(
    interactionProfile: StandardInteractionProfile,
    user: StandardUser,
    identifier: StandardInputIdentifier,
    component: StandardInputComponent,
    location: StandardInputLocation? = null
) = input(interactionProfile.interactionProfile, user.user, identifier.identifier, component.component, location?.location)

