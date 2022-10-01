package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.InputLocation

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

private fun inputLocation(id: String) = InputLocation.newBuilder().setId(id).build()