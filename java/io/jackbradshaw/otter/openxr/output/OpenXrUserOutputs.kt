package io.jackbradshaw.otter.openxr.output

import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Identifier
import io.jackbradshaw.otter.openxr.model.Location

interface OpenXrUserOutputs {
  suspend fun getOutput(user: User): OpenXrUserOutput
}