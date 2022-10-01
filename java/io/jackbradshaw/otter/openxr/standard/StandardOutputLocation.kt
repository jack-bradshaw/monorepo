package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.OutputLocation

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

private fun outputLocation(id: String) = OutputLocation.newBuilder().setId(id).build()