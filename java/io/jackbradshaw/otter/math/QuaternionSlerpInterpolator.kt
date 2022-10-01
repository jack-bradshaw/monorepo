package io.jackbradshaw.otter.math

import com.google.auto.factory.AutoFactory
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sin

@AutoFactory
class QuaternionSlerpInterpolator(private val start: Quaternion, private val end: Quaternion) :
    QuaternionInterpolator {

  private val workingEnd = if (start.dotProduct(end) < 0f) end.negate() else end
  private val theta = acos(abs(start.dotProduct(end)))
  private val inverseOfSinTheta = 1f / sin(theta)

  override suspend fun at(proportion: Float): Quaternion {
    checkProportion(proportion)
    val startScale = sin((1 - proportion) * theta) * inverseOfSinTheta
    val endScale = sin(proportion * theta) * inverseOfSinTheta

    return quaternion(
        scalar = startScale * start.scalar + endScale * workingEnd.scalar,
        iCoefficient = startScale * start.iCoefficient + endScale * workingEnd.iCoefficient,
        jCoefficient = startScale * start.jCoefficient + endScale * workingEnd.jCoefficient,
        kCoefficient = startScale * start.kCoefficient + endScale * workingEnd.kCoefficient)
  }
}

suspend fun singletonQuaternionSlerp(
    start: Quaternion,
    end: Quaternion,
    proportion: Float
): Quaternion = QuaternionSlerpInterpolator(start, end).at(proportion)
