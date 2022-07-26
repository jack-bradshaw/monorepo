package io.matthewbradshaw.jockstrap.math

interface QuaternionInterpolator {
  suspend fun at(proportion: Float): Quaternion
}

fun checkProportion(proportion: Float) {
  if (proportion < 0f || proportion > 1f) {
    throw IllegalArgumentException("Proportion must be between 0 and 1 inclusive, but is $proportion.")
  }
}