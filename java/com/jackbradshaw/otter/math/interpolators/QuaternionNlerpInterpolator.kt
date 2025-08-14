package com.jackbradshaw.otter.math.interpolators

import com.google.auto.factory.AutoFactory
import com.jackbradshaw.otter.math.model.Quaternion
import com.jackbradshaw.otter.math.model.dotProduct
import com.jackbradshaw.otter.math.model.minus
import com.jackbradshaw.otter.math.model.normalize
import com.jackbradshaw.otter.math.model.plus
import com.jackbradshaw.otter.math.model.times

@AutoFactory
class QuaternionNlerpInterpolator(private val start: Quaternion, private val end: Quaternion) :
    QuaternionInterpolator {

  private val dotProduct = start.dotProduct(end)

  override suspend fun at(proportion: Float): Quaternion {
    return if (dotProduct < 0.0f) {
      ((1f - proportion) * start - proportion * end).normalize()
    } else {
      ((1f - proportion) * start + proportion * end).normalize()
    }
  }
}

suspend fun singletonQuaternionNlerp(
    start: Quaternion,
    end: Quaternion,
    proportion: Float
): Quaternion = QuaternionNlerpInterpolator(start, end).at(proportion)
