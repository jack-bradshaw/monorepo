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
import io.jackbradshaw.omnixr.model.InputIdentifier
import io.jackbradshaw.omnixr.model.inputIdentifier
/*
 * The standard input identifiers defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardInputIdentifier(val identifier: InputIdentifier) {
  SYSTEM(inputIdentifier("system")),
  TRACKPAD(inputIdentifier("trackpad")),
  THUMBSTICK(inputIdentifier("thumbstick")),
  JOYSTICK(inputIdentifier("joystick")),
  TRIGGER(inputIdentifier("trigger")),
  THROTTLE(inputIdentifier("throttle")),
  TRACKBALL(inputIdentifier("trackball")),
  PEDAL(inputIdentifier("pedal")),
  WHEEL(inputIdentifier("wheel")),
  DPAD_UP(inputIdentifier("dpad_up")),
  DPAD_DOWN(inputIdentifier("dpad_down")),
  DPAD_LEFT(inputIdentifier("dpad_left")),
  DPAD_RIGHT(inputIdentifier("dpad_right")),
  DIAMOND_UP(inputIdentifier("diamond_up")),
  DIAMOND_DOWN(inputIdentifier("diamond_down")),
  DIAMOND_LEFT(inputIdentifier("diamond_left")),
  DIAMONG_RIGHT(inputIdentifier("diamond_right")),
  A(inputIdentifier("a")),
  B(inputIdentifier("b")),
  X(inputIdentifier("c")),
  Y(inputIdentifier("d")),
  START(inputIdentifier("start")),
  HOME(inputIdentifier("home")),
  END(inputIdentifier("end")),
  SELECT(inputIdentifier("select")),
  VOLUME_UP(inputIdentifier("volume_up")),
  VOLUME_DOWN(inputIdentifier("volume_down")),
  MUTE_MIC(inputIdentifier("mute_mic")),
  PLAY_PAUSE(inputIdentifier("play_pause")),
  MENU(inputIdentifier("menu")),
  VIEW(inputIdentifier("view")),
  BACK(inputIdentifier("back")),
  THUMBREST(inputIdentifier("thumbrest")),
  SHOULDER(inputIdentifier("shoulder")),
  SQUEEZE(inputIdentifier("squeeze")),
  GRIP(inputIdentifier("grip")),
  AIM(inputIdentifier("aim"));

  companion object {
    private val reverse = StandardInputIdentifier.values().map { it.identifier to it }.toMap()
    fun fromInputIdentifier(identifier: InputIdentifier): StandardInputIdentifier? {
      return reverse[identifier]
    }
  }
}