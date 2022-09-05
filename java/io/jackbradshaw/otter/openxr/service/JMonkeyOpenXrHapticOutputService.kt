package io.jackbradshaw.otter.openxr.handler

import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.Output

interface JMonkeyOpenXrHapticOutputService<C> {
  suspend fun output(output: Output, configuration: C)
}