package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.User

/*
 * The standard users defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardUser(val user: User) {
  HEAD(user("head")),
  LEFT_HAND(user("hand/left")),
  RIGHT_HAND(user("hand/right")),
  GAMEPAD(user("gamepad")),
  TREADMILL(user("treadmill"));

  companion object {
    private val reverse = StandardUser.values().map { it.user to it }.toMap()

    fun fromUser(user: User): StandardUser? {
      return reverse[user]
    }
  }
}

private fun user(id: String) = User.newBuilder().setId(id).build()
