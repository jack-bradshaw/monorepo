package io.jackbradshaw.otter.openxr.input

import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Identifier
import io.jackbradshaw.otter.openxr.model.Location

interface OpenXrUserInputs {
  suspend fun getInput(
      user: User,
      identifer: Identifier,
      location: Location? = null,
      profile: InteractionProfile
  ): OpenXrUserInput
}