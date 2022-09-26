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
import io.jackbradshaw.otter.openxr.model.InputLocation
import io.jackbradshaw.otter.openxr.model.inputLocation

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