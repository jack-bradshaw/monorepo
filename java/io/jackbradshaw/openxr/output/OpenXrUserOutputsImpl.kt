package io.jackbradshaw.otter.openxr.output

import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Identifier
import io.jackbradshaw.otter.openxr.model.Location
import java.util.concurrent.ConcurrentHashMap

class OpenXrUserOutputsImpl(
    private val outputFactory: OpenXrUserOutputImplFactory
) : OpenXrUserOutputs {

  private val outputs = ConcurrentHashMap<User, OpenXrUserOutput>()

  override suspend fun getOutput(user: User): OpenXrUserOutput {
    return outputs.getOrPut(user) { outputFactory.create(user) }
  }
}
