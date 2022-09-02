package io.jackbradshaw.otter.openxr.output

interface OpenXrUserOutput {
  suspend fun engageHapticFeedback(intensityProportion: Float)
  suspend fun disengageHapticFeedback()
}