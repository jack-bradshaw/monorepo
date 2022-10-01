package io.jackbradshaw.otter.math

import com.google.auto.factory.AutoFactory

@AutoFactory
class QuaternionNlerpInterpolator(
    private val start: Quaternion,
    private val end: Quaternion
) : QuaternionInterpolator {

  private val dotProduct = start.dotProduct(end)

  override suspend fun at(proportion: Float): Quaternion {
    return if (dotProduct < 0.0f) {
      ((1f - proportion) * start - proportion * end).normalize()
    } else {
      ((1f - proportion) * start + proportion * end).normalize()
    }
  }
}

suspend fun singletonQuaternionNlerp(start: Quaternion, end: Quaternion, proportion: Float): Quaternion =
    QuaternionNlerpInterpolator(start, end).at(proportion)