package io.jackbradshaw.otter.openxr.standard

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

private fun outputIdentifier(id: String) = OutputIdentifier.newBuilder().setId(id).build()
