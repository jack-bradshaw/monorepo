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
import io.jackbradshaw.clearxr.model.InputIdentifier
import io.jackbradshaw.clearxr.model.inputIdentifier
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