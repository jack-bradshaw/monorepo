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

import io.jackbradshaw.openxr.model.outputLocation
import io.jackbradshaw.openxr.model.outputIdentifier
import io.jackbradshaw.openxr.model.OutputLocation
import io.jackbradshaw.openxr.model.OutputIdentifier

/*
 * The standard input components defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardOutputLocation(val location: OutputLocation) {
  LEFT(outputLocation("left")),
  RIGHT(outputLocation("right")),
  LEFT_TRIGGER(outputLocation("left_trigger")),
  RIGHT_TRIGGER(outputLocation("right_trigger"));

  companion object {
    private val reverse = StandardOutputLocation.values().map { it.location to it }.toMap()
    fun fromOutputLocation(location: OutputLocation): StandardOutputLocation? {
      return reverse[location]
    }
  }
}