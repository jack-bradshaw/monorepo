package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.InputComponent
import io.jackbradshaw.otter.openxr.model.inputComponent

/*
 * The standard input components defined by
 * [version 1.0 of the OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf).
 */
enum class StandardInputComponent(val component: InputComponent) {
  CLICK(inputComponent("click")),
  TOUCH(inputComponent("touch")),
  FORCE(inputComponent("force")),
  VALUE(inputComponent("value")),
  X(inputComponent("x")),
  Y(inputComponent("y")),
  TWIST(inputComponent("twist")),
  POSE(inputComponent("pose"));

  companion object {
    private val reverse = StandardInputComponent.values().map { it.component to it }.toMap()
    fun fromInputComponent(component: InputComponent): StandardInputComponent? {
      return reverse[component]
    }
  }
}