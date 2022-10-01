package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.InputIdentifier

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

private fun inputIdentifier(id: String) = InputIdentifier.newBuilder().setId(id).build()