package io.jackbradshaw.otter.openxr.handler

interface JMonkeyOpenXrInputService : OpenXrService {
  fun inputs(): Flow<Input>
  suspend fun output(output: Output)
}