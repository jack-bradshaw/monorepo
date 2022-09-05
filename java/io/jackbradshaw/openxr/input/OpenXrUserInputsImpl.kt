package io.jackbradshaw.otter.openxr.input

import io.jackbradshaw.otter.openxr.constants.OpenXrConstants
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Identifier
import io.jackbradshaw.otter.openxr.model.Location
import java.util.concurrent.ConcurrentHashMap

class OpenXrUserInputsImpl(
    private val inputFactory: OpenXrUserInputImplFactory
) : OpenXrUserInputs {

  private val inputs = ConcurrentHashMap<CompositeKey, OpenXrUserInput>()

  override suspend fun getInput(
      user: User,
      identifer: Identifier,
      location: Location?,
      profile: InteractionProfile
  ): OpenXrUserInput {
    return inputs.getOrPut(CompositeKey(user, identifer, location)) {
      inputFactory.create(user, identifer, location, profile)
    }
  }
}

/**
 * Combined multiple items into a single key.
 */
private data class CompositeKey(
    private val user: User,
    private val identifier: Identifier,
    private val location: Location?
)