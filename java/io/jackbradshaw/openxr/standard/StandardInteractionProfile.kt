package io.jackbradshaw.openxr.standard

import io.jackbradshaw.openxr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.openxr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.openxr.standard.StandardUser.GAMEPAD
import io.jackbradshaw.openxr.standard.StandardUser.HEAD
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.SELECT
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.MENU
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.SYSTEM
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.SQUEEZE
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.TRIGGER
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.VIEW
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.BACK
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.DPAD_DOWN
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.DPAD_UP
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.DPAD_LEFT
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.DPAD_RIGHT
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.A
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.B
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.SHOULDER
import io.jackbradshaw.openxr.standard.StandardInputLocation.LEFT
import io.jackbradshaw.openxr.standard.StandardInputLocation.RIGHT
import io.jackbradshaw.openxr.standard.StandardInputComponent.TOUCH
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.TRACKPAD
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.THUMBSTICK
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.THUMBREST
import io.jackbradshaw.openxr.standard.StandardOutputIdentifier.HAPTIC
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.GRIP
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.AIM
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.VOLUME_UP
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.VOLUME_DOWN
import io.jackbradshaw.openxr.standard.StandardInputIdentifier.MUTE_MIC
import io.jackbradshaw.openxr.standard.StandardInputComponent.CLICK
import io.jackbradshaw.openxr.standard.StandardInputComponent.POSE
import io.jackbradshaw.openxr.standard.StandardInputComponent.FORCE
import io.jackbradshaw.openxr.standard.StandardInputComponent.VALUE
import io.jackbradshaw.openxr.standard.StandardOutputLocation.LEFT_TRIGGER
import io.jackbradshaw.openxr.standard.StandardOutputLocation.RIGHT_TRIGGER
import io.jackbradshaw.openxr.model.interactionProfile
import io.jackbradshaw.openxr.model.InteractionProfile
import io.jackbradshaw.openxr.model.input
import io.jackbradshaw.openxr.model.output

private fun input(
    user: StandardUser,
    identifier: StandardInputIdentifier,
    component: StandardInputComponent,
    location: StandardInputLocation? = null) = input(user.user, identifier.identifier, component.component, location?.location)

private fun output(user: StandardUser, identifier: StandardOutputIdentifier, location: StandardOutputLocation? = null) = output(user.user, identifier.identifier, location?.location)

/*
 * The standard interaction profiles defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardInteractionProfile(val interactionProfile: InteractionProfile) {
  KHRONOS_SIMPLE_CONTROLLER(interactionProfile(
      vendor = "khr",
      controller = "simple_controller",
      inputs = setOf(
          input(LEFT_HAND, SELECT, CLICK),
          input(LEFT_HAND, MENU, CLICK),
          input(LEFT_HAND, GRIP, POSE),
          input(LEFT_HAND, AIM, POSE),
          input(RIGHT_HAND, SELECT, CLICK),
          input(RIGHT_HAND, MENU, CLICK),
          input(RIGHT_HAND, GRIP, POSE),
          input(RIGHT_HAND, AIM, POSE),
      ),
      outputs = setOf(
          output(LEFT_HAND, HAPTIC),
          output(RIGHT_HAND, HAPTIC)
      ))),
  GOOGLE_DAYDREAM_CONTROLLER(interactionProfile(vendor = "google", controller = "daydream_controller", inputs = setOf(
      input(LEFT_HAND, SELECT, CLICK),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(LEFT_HAND, TRACKPAD, CLICK),
      input(LEFT_HAND, TRACKPAD, TOUCH),
      input(LEFT_HAND, GRIP, POSE),
      input(LEFT_HAND, AIM, POSE),
      input(RIGHT_HAND, SELECT, CLICK),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(RIGHT_HAND, TRACKPAD, CLICK),
      input(RIGHT_HAND, TRACKPAD, TOUCH),
      input(RIGHT_HAND, GRIP, POSE),
      input(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf())),
  HTC_VIVE_CONTROLLER(interactionProfile(vendor = "htc", controller = "vive_controller", inputs = setOf(
      input(LEFT_HAND, SYSTEM, CLICK),
      input(LEFT_HAND, SQUEEZE, CLICK),
      input(LEFT_HAND, MENU, CLICK),
      input(LEFT_HAND, TRIGGER, CLICK),
      input(LEFT_HAND, TRIGGER, VALUE),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(LEFT_HAND, TRACKPAD, CLICK),
      input(LEFT_HAND, TRACKPAD, TOUCH),
      input(LEFT_HAND, GRIP, POSE),
      input(LEFT_HAND, AIM, POSE),
      input(RIGHT_HAND, SYSTEM, CLICK),
      input(RIGHT_HAND, SQUEEZE, CLICK),
      input(RIGHT_HAND, MENU, CLICK),
      input(RIGHT_HAND, TRIGGER, CLICK),
      input(RIGHT_HAND, TRIGGER, VALUE),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(RIGHT_HAND, TRACKPAD, CLICK),
      input(RIGHT_HAND, TRACKPAD, TOUCH),
      input(RIGHT_HAND, GRIP, POSE),
      input(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf(
      output(LEFT_HAND, HAPTIC), output(RIGHT_HAND, HAPTIC)
  ))),
  HTC_VIVE_PRO(interactionProfile(vendor = "htc", controller = "vive_pro", inputs = setOf(
      input(HEAD, SYSTEM, CLICK),
      input(HEAD, VOLUME_UP, CLICK),
      input(HEAD, VOLUME_DOWN, CLICK),
      input(HEAD, MUTE_MIC, CLICK),
  ), outputs = setOf())),
  MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER(interactionProfile(
      vendor = "microsoft",
      controller = "motion_controller",
      inputs = setOf(
          input(LEFT_HAND, MENU, CLICK),
          input(LEFT_HAND, SQUEEZE, CLICK),
          input(LEFT_HAND, TRIGGER, VALUE),
          input(LEFT_HAND, THUMBSTICK, StandardInputComponent.X),
          input(LEFT_HAND, THUMBSTICK, StandardInputComponent.Y),
          input(LEFT_HAND, THUMBSTICK, CLICK),
          input(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
          input(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
          input(LEFT_HAND, TRACKPAD, CLICK),
          input(LEFT_HAND, TRACKPAD, TOUCH),
          input(LEFT_HAND, GRIP, POSE),
          input(LEFT_HAND, AIM, POSE),
          input(RIGHT_HAND, MENU, CLICK),
          input(RIGHT_HAND, SQUEEZE, CLICK),
          input(RIGHT_HAND, TRIGGER, VALUE),
          input(RIGHT_HAND, THUMBSTICK, StandardInputComponent.X),
          input(RIGHT_HAND, THUMBSTICK, StandardInputComponent.Y),
          input(RIGHT_HAND, THUMBSTICK, CLICK),
          input(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
          input(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
          input(RIGHT_HAND, TRACKPAD, CLICK),
          input(RIGHT_HAND, TRACKPAD, TOUCH),
          input(RIGHT_HAND, GRIP, POSE),
          input(RIGHT_HAND, AIM, POSE),
      ), outputs = setOf(
      output(LEFT_HAND, HAPTIC), output(RIGHT_HAND, HAPTIC)
  ))),
  MICROSOFT_XBOX_CONTROLLER(interactionProfile(vendor = "microsoft", controller = "xbox_controller", inputs = setOf(
      input(GAMEPAD, MENU, CLICK),
      input(GAMEPAD, VIEW, CLICK),
      input(GAMEPAD, A, CLICK),
      input(GAMEPAD, B, CLICK),
      input(GAMEPAD, StandardInputIdentifier.Y, CLICK),
      input(GAMEPAD, StandardInputIdentifier.X, CLICK),
      input(GAMEPAD, DPAD_UP, CLICK),
      input(GAMEPAD, DPAD_DOWN, CLICK),
      input(GAMEPAD, DPAD_LEFT, CLICK),
      input(GAMEPAD, DPAD_RIGHT, CLICK),
      input(GAMEPAD, SHOULDER, CLICK, LEFT),
      input(GAMEPAD, SHOULDER, CLICK, RIGHT),
      input(GAMEPAD, THUMBSTICK, CLICK, LEFT),
      input(GAMEPAD, THUMBSTICK, CLICK, RIGHT),
      input(GAMEPAD, THUMBSTICK, VALUE, LEFT),
      input(GAMEPAD, THUMBSTICK, VALUE, RIGHT),
      input(GAMEPAD, THUMBSTICK, StandardInputComponent.X, LEFT),
      input(GAMEPAD, THUMBSTICK, StandardInputComponent.X, RIGHT),
      input(GAMEPAD, THUMBSTICK, StandardInputComponent.Y, LEFT),
      input(GAMEPAD, THUMBSTICK, StandardInputComponent.Y, RIGHT),
      input(GAMEPAD, TRIGGER, CLICK, LEFT),
      input(GAMEPAD, TRIGGER, CLICK, RIGHT),
  ), outputs = setOf(
      output(GAMEPAD, HAPTIC, StandardOutputLocation.LEFT),
      output(GAMEPAD, HAPTIC, StandardOutputLocation.RIGHT),
      output(GAMEPAD, HAPTIC, LEFT_TRIGGER),
      output(GAMEPAD, HAPTIC, RIGHT_TRIGGER),
  ))),
  OCCULUS_GO_CONTROLLER(interactionProfile(vendor = "oculus", controller = "go_controller", inputs = setOf(
      input(LEFT_HAND, SYSTEM, CLICK),
      input(LEFT_HAND, TRIGGER, CLICK),
      input(LEFT_HAND, BACK, CLICK),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(LEFT_HAND, TRACKPAD, CLICK),
      input(LEFT_HAND, TRACKPAD, TOUCH),
      input(LEFT_HAND, GRIP, POSE),
      input(LEFT_HAND, AIM, POSE),
      input(RIGHT_HAND, SYSTEM, CLICK),
      input(RIGHT_HAND, TRIGGER, CLICK),
      input(RIGHT_HAND, BACK, CLICK),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(RIGHT_HAND, TRACKPAD, CLICK),
      input(RIGHT_HAND, TRACKPAD, TOUCH),
      input(RIGHT_HAND, GRIP, POSE),
      input(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf())),
  OCCULUS_TOUCH_CONTROLLER(interactionProfile(vendor = "oculus", controller = "touch_controller", inputs = setOf(
      input(LEFT_HAND, StandardInputIdentifier.X, CLICK),
      input(LEFT_HAND, StandardInputIdentifier.X, TOUCH),
      input(LEFT_HAND, StandardInputIdentifier.Y, CLICK),
      input(LEFT_HAND, StandardInputIdentifier.Y, TOUCH),
      input(LEFT_HAND, MENU, CLICK),
      input(LEFT_HAND, SQUEEZE, VALUE),
      input(LEFT_HAND, TRIGGER, VALUE),
      input(LEFT_HAND, TRIGGER, TOUCH),
      input(LEFT_HAND, THUMBSTICK, StandardInputComponent.X),
      input(LEFT_HAND, THUMBSTICK, StandardInputComponent.Y),
      input(LEFT_HAND, THUMBSTICK, CLICK),
      input(LEFT_HAND, THUMBSTICK, TOUCH),
      input(LEFT_HAND, THUMBREST, TOUCH),
      input(LEFT_HAND, GRIP, POSE),
      input(LEFT_HAND, AIM, POSE),
      input(RIGHT_HAND, A, CLICK),
      input(RIGHT_HAND, A, TOUCH),
      input(RIGHT_HAND, B, CLICK),
      input(RIGHT_HAND, B, TOUCH),
      input(RIGHT_HAND, SYSTEM, CLICK),
      input(RIGHT_HAND, SQUEEZE, VALUE),
      input(RIGHT_HAND, TRIGGER, VALUE),
      input(RIGHT_HAND, TRIGGER, TOUCH),
      input(RIGHT_HAND, THUMBSTICK, StandardInputComponent.X),
      input(RIGHT_HAND, THUMBSTICK, StandardInputComponent.Y),
      input(RIGHT_HAND, THUMBSTICK, CLICK),
      input(RIGHT_HAND, THUMBSTICK, TOUCH),
      input(RIGHT_HAND, THUMBREST, TOUCH),
      input(RIGHT_HAND, GRIP, POSE),
      input(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf(
      output(LEFT_HAND, HAPTIC), output(RIGHT_HAND, HAPTIC)
  ))),
  VALVE_INDEX_CONTROLLER(interactionProfile(vendor = "valve", controller = "index_controller", inputs = setOf(
      input(LEFT_HAND, SYSTEM, CLICK),
      input(LEFT_HAND, SYSTEM, TOUCH),
      input(LEFT_HAND, A, CLICK),
      input(LEFT_HAND, A, TOUCH),
      input(LEFT_HAND, B, CLICK),
      input(LEFT_HAND, B, TOUCH),
      input(LEFT_HAND, SQUEEZE, VALUE),
      input(LEFT_HAND, SQUEEZE, FORCE),
      input(LEFT_HAND, TRIGGER, CLICK),
      input(LEFT_HAND, TRIGGER, VALUE),
      input(LEFT_HAND, TRIGGER, TOUCH),
      input(LEFT_HAND, THUMBSTICK, StandardInputComponent.X),
      input(LEFT_HAND, THUMBSTICK, StandardInputComponent.Y),
      input(LEFT_HAND, THUMBSTICK, CLICK),
      input(LEFT_HAND, THUMBSTICK, TOUCH),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      input(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(LEFT_HAND, TRACKPAD, FORCE),
      input(LEFT_HAND, TRACKPAD, TOUCH),
      input(LEFT_HAND, GRIP, POSE),
      input(LEFT_HAND, AIM, POSE),
      input(RIGHT_HAND, SYSTEM, CLICK),
      input(RIGHT_HAND, SYSTEM, TOUCH),
      input(RIGHT_HAND, A, CLICK),
      input(RIGHT_HAND, A, TOUCH),
      input(RIGHT_HAND, B, CLICK),
      input(RIGHT_HAND, B, TOUCH),
      input(RIGHT_HAND, SQUEEZE, VALUE),
      input(RIGHT_HAND, SQUEEZE, FORCE),
      input(RIGHT_HAND, TRIGGER, CLICK),
      input(RIGHT_HAND, TRIGGER, VALUE),
      input(RIGHT_HAND, TRIGGER, TOUCH),
      input(RIGHT_HAND, THUMBSTICK, StandardInputComponent.X),
      input(RIGHT_HAND, THUMBSTICK, StandardInputComponent.Y),
      input(RIGHT_HAND, THUMBSTICK, CLICK),
      input(RIGHT_HAND, THUMBSTICK, TOUCH),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      input(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      input(RIGHT_HAND, TRACKPAD, FORCE),
      input(RIGHT_HAND, TRACKPAD, TOUCH),
      input(RIGHT_HAND, GRIP, POSE),
      input(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf(
      output(LEFT_HAND, HAPTIC), output(RIGHT_HAND, HAPTIC)
  )));

  companion object {
    private val reverse = StandardInteractionProfile.values().map { it.interactionProfile to it }.toMap()
    fun fromInteractionProfile(interactionProfile: InteractionProfile): StandardInteractionProfile? {
      return reverse[interactionProfile]
    }
  }
}