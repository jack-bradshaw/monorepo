package io.jackbradshaw.omnixr.standard

import io.jackbradshaw.omnixr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.omnixr.standard.StandardUser.RIGHT_HAND
import io.jackbradshaw.omnixr.standard.StandardUser.GAMEPAD
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

import io.jackbradshaw.omnixr.model.outputLocation
import io.jackbradshaw.omnixr.model.outputIdentifier
import io.jackbradshaw.omnixr.model.OutputLocation
import io.jackbradshaw.omnixr.model.OutputIdentifier

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