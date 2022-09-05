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
import io.jackbradshaw.otter.openxr.model.outputLocation
import io.jackbradshaw.otter.openxr.model.outputIdentifier
import io.jackbradshaw.otter.openxr.model.OutputLocation
import io.jackbradshaw.otter.openxr.model.OutputIdentifier

/*
 * The standard output identifiers defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardOutputIdentifier(val identifier: OutputIdentifier) {
  HAPTIC(outputIdentifier("haptic"));

  companion object {
    private val reverse = StandardOutputIdentifier.values().map { it.identifier to it }.toMap()
    fun fromOutputIdentifer(identifier: OutputIdentifier): StandardOutputIdentifier? {
      return reverse[identifier]
    }
  }
}