package io.jackbradshaw.clearxr.standard

import io.jackbradshaw.clearxr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.clearxr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.clearxr.standard.StandardUser.GAMEPAD
import io.jackbradshaw.clearxr.standard.StandardUser.HEAD
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.SELECT
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.MENU
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.SYSTEM
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.SQUEEZE
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.TRIGGER
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.VIEW
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.BACK
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.DPAD_DOWN
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.DPAD_UP
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.DPAD_LEFT
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.DPAD_RIGHT
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.A
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.B
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.SHOULDER
import io.jackbradshaw.clearxr.standard.StandardInputLocation.LEFT
import io.jackbradshaw.clearxr.standard.StandardInputLocation.RIGHT
import io.jackbradshaw.clearxr.standard.StandardInputComponent.TOUCH
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.TRACKPAD
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.THUMBSTICK
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.THUMBREST
import io.jackbradshaw.clearxr.standard.StandardOutputIdentifier.HAPTIC
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.GRIP
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.AIM
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.VOLUME_UP
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.VOLUME_DOWN
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.MUTE_MIC
import io.jackbradshaw.clearxr.standard.StandardInputComponent.CLICK
import io.jackbradshaw.clearxr.standard.StandardInputComponent.POSE
import io.jackbradshaw.clearxr.standard.StandardInputComponent.FORCE
import io.jackbradshaw.clearxr.standard.StandardInputComponent.VALUE
import io.jackbradshaw.clearxr.standard.StandardOutputLocation.LEFT_TRIGGER
import io.jackbradshaw.clearxr.standard.StandardOutputLocation.RIGHT_TRIGGER
import io.jackbradshaw.clearxr.model.InputLocation
import io.jackbradshaw.clearxr.model.inputLocation

/*
 * The standard input locations defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardInputLocation(val location: InputLocation) {
  LEFT(inputLocation("left")),
  RIGHT(inputLocation("right")),
  UPPER(inputLocation("upper")),
  LOWER(inputLocation("lower")),
  LEFT_UPPER(inputLocation("upper_left")),
  LEFT_LOWER(inputLocation("lower_left")),
  RIGHT_UPPER(inputLocation("upper_right")),
  RIGHT_LOWER(inputLocation("lower_right"));

  companion object {
    private val reverse = StandardInputLocation.values().map { it.location to it }.toMap()
    fun fromInputLocation(location: InputLocation): StandardInputLocation? {
      return reverse[location]
    }
  }
}