package io.jackbradshaw.otter.openxr.handler

interface JMonkeyOpenXrInputService : OpenXrService {
  fun inputs(): Flow<Input>
}