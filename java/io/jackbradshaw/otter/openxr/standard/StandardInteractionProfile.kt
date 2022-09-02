package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.otter.openxr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.otter.openxr.standard.StandardUser.GAMEPAD
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
import io.jackbradshaw.otter.openxr.standard.StandardInputLocation.LEFT
import io.jackbradshaw.otter.openxr.standard.StandardInputLocation.RIGHT
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

private fun inputSpec(user: StandardUser1_0, identifier: StandardInputIdentifier, component: StandardInputComponent, location: StandardInputLocation? = null) = inputSpec(user.user, identifier.identifier, component.component, location?.location)

private fun outputSpec(user: StandardUser1_0, identifier: StandardOutputIdentifier, location: StandardOutputLocation? = null) = outputSpec(user.user, identifier.identifier, location?.location)

/*
 * The standard interaction profiles defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardInteractionProfile(val interactionProfile: InteractionProfile) {
  KHRONOS_SIMPLE_CONTROLLER(interactionProfile(
      vendor = "khr",
      controller = "simple_controller",
      inputs = setOf(
          inputSpec(LEFT_HAND, SELECT, CLICK),
          inputSpec(LEFT_HAND, MENU, CLICK),
          inputSpec(LEFT_HAND, GRIP, POSE),
          inputSpec(LEFT_HAND, AIM, POSE),
          inputSpec(RIGHT_HAND, SELECT, CLICK),
          inputSpec(RIGHT_HAND, MENU, CLICK),
          inputSpec(RIGHT_HAND, GRIP, POSE),
          inputSpec(RIGHT_HAND, AIM, POSE),
      ),
      outputs = setOf(
          outputSpec(LEFT_HAND, HAPTIC),
          outputSpec(RIGHT_HAND, HAPTIC)
      ))),
  GOOGLE_DAYDREAM_CONTROLLER(interactionProfile(vendor = "google", controller = "daydream_controller", inputs = setOf(
      inputSpec(LEFT_HAND, SELECT, CLICK),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(LEFT_HAND, TRACKPAD, CLICK),
      inputSpec(LEFT_HAND, TRACKPAD, TOUCH),
      inputSpec(LEFT_HAND, GRIP, POSE),
      inputSpec(LEFT_HAND, AIM, POSE),
      inputSpec(RIGHT_HAND, SELECT, CLICK),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(RIGHT_HAND, TRACKPAD, CLICK),
      inputSpec(RIGHT_HAND, TRACKPAD, TOUCH),
      inputSpec(RIGHT_HAND, GRIP, POSE),
      inputSpec(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf())),
  HTC_VIVE_CONTROLLER(interactionProfile(vendor = "htc", controller = "vive_controller", inputs = setOf(
      inputSpec(LEFT_HAND, SYSTEM, CLICK),
      inputSpec(LEFT_HAND, SQUEEZE, CLICK),
      inputSpec(LEFT_HAND, MENU, CLICK),
      inputSpec(LEFT_HAND, TRIGGER, CLICK),
      inputSpec(LEFT_HAND, TRIGGER, VALUE),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(LEFT_HAND, TRACKPAD, CLICK),
      inputSpec(LEFT_HAND, TRACKPAD, TOUCH),
      inputSpec(LEFT_HAND, GRIP, POSE),
      inputSpec(LEFT_HAND, AIM, POSE),
      inputSpec(RIGHT_HAND, SYSTEM, CLICK),
      inputSpec(RIGHT_HAND, SQUEEZE, CLICK),
      inputSpec(RIGHT_HAND, MENU, CLICK),
      inputSpec(RIGHT_HAND, TRIGGER, CLICK),
      inputSpec(RIGHT_HAND, TRIGGER, VALUE),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(RIGHT_HAND, TRACKPAD, CLICK),
      inputSpec(RIGHT_HAND, TRACKPAD, TOUCH),
      inputSpec(RIGHT_HAND, GRIP, POSE),
      inputSpec(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf(
      outputSpec(LEFT_HAND, HAPTIC), outputSpec(RIGHT_HAND, HAPTIC)
  ))),
  HTC_VIVE_PRO(interactionProfile(vendor = "htc", controller = "vive_pro", inputs = setOf(
      inputSpec(HEAD, SYSTEM, CLICK),
      inputSpec(HEAD, VOLUME_UP, CLICK),
      inputSpec(HEAD, VOLUME_DOWN, CLICK),
      inputSpec(HEAD, MUTE_MIC, CLICK),
  ), outputs = setOf())),
  MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER(interactionProfile(
      vendor = "microsoft",
      controller = "motion_controller",
      inputs = setOf(
          inputSpec(LEFT_HAND, MENU, CLICK),
          inputSpec(LEFT_HAND, SQUEEZE, CLICK),
          inputSpec(LEFT_HAND, TRIGGER, VALUE),
          inputSpec(LEFT_HAND, THUMBSTICK, StandardInputComponent.X),
          inputSpec(LEFT_HAND, THUMBSTICK, StandardInputComponent.Y),
          inputSpec(LEFT_HAND, THUMBSTICK, CLICK),
          inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
          inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
          inputSpec(LEFT_HAND, TRACKPAD, CLICK),
          inputSpec(LEFT_HAND, TRACKPAD, TOUCH),
          inputSpec(LEFT_HAND, GRIP, POSE),
          inputSpec(LEFT_HAND, AIM, POSE),
          inputSpec(RIGHT_HAND, MENU, CLICK),
          inputSpec(RIGHT_HAND, SQUEEZE, CLICK),
          inputSpec(RIGHT_HAND, TRIGGER, VALUE),
          inputSpec(RIGHT_HAND, THUMBSTICK, StandardInputComponent.X),
          inputSpec(RIGHT_HAND, THUMBSTICK, StandardInputComponent.Y),
          inputSpec(RIGHT_HAND, THUMBSTICK, CLICK),
          inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
          inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
          inputSpec(RIGHT_HAND, TRACKPAD, CLICK),
          inputSpec(RIGHT_HAND, TRACKPAD, TOUCH),
          inputSpec(RIGHT_HAND, GRIP, POSE),
          inputSpec(RIGHT_HAND, AIM, POSE),
      ), outputs = setOf(
      outputSpec(LEFT_HAND, HAPTIC), outputSpec(RIGHT_HAND, HAPTIC)
  ))),
  MICROSOFT_XBOX_CONTROLLER(interactionProfile(vendor = "microsoft", controller = "xbox_controller", inputs = setOf(
      inputSpec(GAMEPAD, MENU, CLICK),
      inputSpec(GAMEPAD, VIEW, CLICK),
      inputSpec(GAMEPAD, A, CLICK),
      inputSpec(GAMEPAD, B, CLICK),
      inputSpec(GAMEPAD, StandardInputIdentifier.Y, CLICK),
      inputSpec(GAMEPAD, StandardInputIdentifier.X, CLICK),
      inputSpec(GAMEPAD, DPAD_UP, CLICK),
      inputSpec(GAMEPAD, DPAD_DOWN, CLICK),
      inputSpec(GAMEPAD, DPAD_LEFT, CLICK),
      inputSpec(GAMEPAD, DPAD_RIGHT, CLICK),
      inputSpec(GAMEPAD, SHOULDER, CLICK, LEFT),
      inputSpec(GAMEPAD, SHOULDER, CLICK, RIGHT),
      inputSpec(GAMEPAD, THUMBSTICK, CLICK, LEFT),
      inputSpec(GAMEPAD, THUMBSTICK, CLICK, RIGHT),
      inputSpec(GAMEPAD, THUMBSTICK, VALUE, LEFT),
      inputSpec(GAMEPAD, THUMBSTICK, VALUE, RIGHT),
      inputSpec(GAMEPAD, THUMBSTICK, StandardInputComponent.X, LEFT),
      inputSpec(GAMEPAD, THUMBSTICK, StandardInputComponent.X, RIGHT),
      inputSpec(GAMEPAD, THUMBSTICK, StandardInputComponent.Y, LEFT),
      inputSpec(GAMEPAD, THUMBSTICK, StandardInputComponent.Y, RIGHT),
      inputSpec(GAMEPAD, TRIGGER, CLICK, LEFT),
      inputSpec(GAMEPAD, TRIGGER, CLICK, RIGHT),
  ), outputs = setOf(
      outputSpec(GAMEPAD, HAPTIC, StandardOutputLocation.LEFT),
      outputSpec(GAMEPAD, HAPTIC, StandardOutputLocation.RIGHT),
      outputSpec(GAMEPAD, HAPTIC, LEFT_TRIGGER),
      outputSpec(GAMEPAD, HAPTIC, RIGHT_TRIGGER),
  ))),
  OCCULUS_GO_CONTROLLER(interactionProfile(vendor = "oculus", controller = "go_controller", inputs = setOf(
      inputSpec(LEFT_HAND, SYSTEM, CLICK),
      inputSpec(LEFT_HAND, TRIGGER, CLICK),
      inputSpec(LEFT_HAND, BACK, CLICK),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(LEFT_HAND, TRACKPAD, CLICK),
      inputSpec(LEFT_HAND, TRACKPAD, TOUCH),
      inputSpec(LEFT_HAND, GRIP, POSE),
      inputSpec(LEFT_HAND, AIM, POSE),
      inputSpec(RIGHT_HAND, SYSTEM, CLICK),
      inputSpec(RIGHT_HAND, TRIGGER, CLICK),
      inputSpec(RIGHT_HAND, BACK, CLICK),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(RIGHT_HAND, TRACKPAD, CLICK),
      inputSpec(RIGHT_HAND, TRACKPAD, TOUCH),
      inputSpec(RIGHT_HAND, GRIP, POSE),
      inputSpec(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf())),
  OCCULUS_TOUCH_CONTROLLER(interactionProfile(vendor = "oculus", controller = "touch_controller", inputs = setOf(
      inputSpec(LEFT_HAND, StandardInputIdentifier.X, CLICK),
      inputSpec(LEFT_HAND, StandardInputIdentifier.X, TOUCH),
      inputSpec(LEFT_HAND, StandardInputIdentifier.Y, CLICK),
      inputSpec(LEFT_HAND, StandardInputIdentifier.Y, TOUCH),
      inputSpec(LEFT_HAND, MENU, CLICK),
      inputSpec(LEFT_HAND, SQUEEZE, VALUE),
      inputSpec(LEFT_HAND, TRIGGER, VALUE),
      inputSpec(LEFT_HAND, TRIGGER, TOUCH),
      inputSpec(LEFT_HAND, THUMBSTICK, StandardInputComponent.X),
      inputSpec(LEFT_HAND, THUMBSTICK, StandardInputComponent.Y),
      inputSpec(LEFT_HAND, THUMBSTICK, CLICK),
      inputSpec(LEFT_HAND, THUMBSTICK, TOUCH),
      inputSpec(LEFT_HAND, THUMBREST, TOUCH),
      inputSpec(LEFT_HAND, GRIP, POSE),
      inputSpec(LEFT_HAND, AIM, POSE),
      inputSpec(RIGHT_HAND, A, CLICK),
      inputSpec(RIGHT_HAND, A, TOUCH),
      inputSpec(RIGHT_HAND, B, CLICK),
      inputSpec(RIGHT_HAND, B, TOUCH),
      inputSpec(RIGHT_HAND, SYSTEM, CLICK),
      inputSpec(RIGHT_HAND, SQUEEZE, VALUE),
      inputSpec(RIGHT_HAND, TRIGGER, VALUE),
      inputSpec(RIGHT_HAND, TRIGGER, TOUCH),
      inputSpec(RIGHT_HAND, THUMBSTICK, StandardInputComponent.X),
      inputSpec(RIGHT_HAND, THUMBSTICK, StandardInputComponent.Y),
      inputSpec(RIGHT_HAND, THUMBSTICK, CLICK),
      inputSpec(RIGHT_HAND, THUMBSTICK, TOUCH),
      inputSpec(RIGHT_HAND, THUMBREST, TOUCH),
      inputSpec(RIGHT_HAND, GRIP, POSE),
      inputSpec(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf(
      outputSpec(LEFT_HAND, HAPTIC), outputSpec(RIGHT_HAND, HAPTIC)
  ))),
  VALVE_INDEX_CONTROLLER(interactionProfile(vendor = "valve", controller = "index_controller", inputs = setOf(
      inputSpec(LEFT_HAND, SYSTEM, CLICK),
      inputSpec(LEFT_HAND, SYSTEM, TOUCH),
      inputSpec(LEFT_HAND, A, CLICK),
      inputSpec(LEFT_HAND, A, TOUCH),
      inputSpec(LEFT_HAND, B, CLICK),
      inputSpec(LEFT_HAND, B, TOUCH),
      inputSpec(LEFT_HAND, SQUEEZE, VALUE),
      inputSpec(LEFT_HAND, SQUEEZE, FORCE),
      inputSpec(LEFT_HAND, TRIGGER, CLICK),
      inputSpec(LEFT_HAND, TRIGGER, VALUE),
      inputSpec(LEFT_HAND, TRIGGER, TOUCH),
      inputSpec(LEFT_HAND, THUMBSTICK, StandardInputComponent.X),
      inputSpec(LEFT_HAND, THUMBSTICK, StandardInputComponent.Y),
      inputSpec(LEFT_HAND, THUMBSTICK, CLICK),
      inputSpec(LEFT_HAND, THUMBSTICK, TOUCH),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(LEFT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(LEFT_HAND, TRACKPAD, FORCE),
      inputSpec(LEFT_HAND, TRACKPAD, TOUCH),
      inputSpec(LEFT_HAND, GRIP, POSE),
      inputSpec(LEFT_HAND, AIM, POSE),
      inputSpec(RIGHT_HAND, SYSTEM, CLICK),
      inputSpec(RIGHT_HAND, SYSTEM, TOUCH),
      inputSpec(RIGHT_HAND, A, CLICK),
      inputSpec(RIGHT_HAND, A, TOUCH),
      inputSpec(RIGHT_HAND, B, CLICK),
      inputSpec(RIGHT_HAND, B, TOUCH),
      inputSpec(RIGHT_HAND, SQUEEZE, VALUE),
      inputSpec(RIGHT_HAND, SQUEEZE, FORCE),
      inputSpec(RIGHT_HAND, TRIGGER, CLICK),
      inputSpec(RIGHT_HAND, TRIGGER, VALUE),
      inputSpec(RIGHT_HAND, TRIGGER, TOUCH),
      inputSpec(RIGHT_HAND, THUMBSTICK, StandardInputComponent.X),
      inputSpec(RIGHT_HAND, THUMBSTICK, StandardInputComponent.Y),
      inputSpec(RIGHT_HAND, THUMBSTICK, CLICK),
      inputSpec(RIGHT_HAND, THUMBSTICK, TOUCH),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.X),
      inputSpec(RIGHT_HAND, TRACKPAD, StandardInputComponent.Y),
      inputSpec(RIGHT_HAND, TRACKPAD, FORCE),
      inputSpec(RIGHT_HAND, TRACKPAD, TOUCH),
      inputSpec(RIGHT_HAND, GRIP, POSE),
      inputSpec(RIGHT_HAND, AIM, POSE),
  ), outputs = setOf(
      outputSpec(LEFT_HAND, HAPTIC), outputSpec(RIGHT_HAND, HAPTIC)
  )));
}