package io.jackbradshaw.jockstrap.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun quaternion(scalar: Number = 1f, iCoefficient: Number = 0f, jCoefficient: Number = 0f, kCoefficient: Number = 0f) =
  Quaternion
    .newBuilder()
    .setScalar(scalar.toFloat())
    .setICoefficient(iCoefficient.toFloat())
    .setJCoefficient(jCoefficient.toFloat())
    .setKCoefficient(kCoefficient.toFloat())
    .build()

fun quaternion(angleRadians: Float = 0f, axis: Vector = zeroVector): Quaternion {
  val normalizedVector = axis.normalize()
  return quaternion(
    scalar = cos(angleRadians),
    iCoefficient = normalizedVector.x * sin(angleRadians / 2),
    jCoefficient = normalizedVector.y * sin(angleRadians / 2),
    kCoefficient = normalizedVector.z * sin(angleRadians / 2)
  )
}

operator fun Quaternion.plus(other: Quaternion): Quaternion = quaternion(
  scalar + other.scalar,
  iCoefficient = iCoefficient + other.iCoefficient,
  jCoefficient = jCoefficient + other.jCoefficient,
  kCoefficient = kCoefficient + other.kCoefficient
)

operator fun Quaternion.plus(scalar: Number): Quaternion = quaternion(
  this.scalar + scalar.toFloat(),
  iCoefficient,
  jCoefficient,
  kCoefficient
)

operator fun Number.plus(quaternion: Quaternion): Quaternion = quaternion + this

operator fun Quaternion.minus(other: Quaternion): Quaternion = quaternion(
  scalar + other.scalar,
  iCoefficient = iCoefficient - other.iCoefficient,
  jCoefficient = jCoefficient - other.jCoefficient,
  kCoefficient = kCoefficient - other.kCoefficient
)

operator fun Quaternion.minus(scalar: Number): Quaternion = quaternion(
  this.scalar - scalar.toFloat(),
  iCoefficient,
  jCoefficient,
  kCoefficient
)

operator fun Number.minus(quaternion: Quaternion): Quaternion = quaternion - this

/**
 * Calculates the [Hamilton product](https://en.wikipedia.org/wiki/Quaternion#Hamilton_product) of this Quaternion with [other].
 */
operator fun Quaternion.times(other: Quaternion): Quaternion = quaternion(
  scalar = this.scalar * other.scalar
      - this.iCoefficient * other.iCoefficient
      - this.jCoefficient * other.jCoefficient
      - this.kCoefficient * other.kCoefficient,
  iCoefficient = this.scalar * other.iCoefficient
      + this.iCoefficient * other.scalar
      + this.jCoefficient * other.kCoefficient
      - this.kCoefficient * other.jCoefficient,
  jCoefficient = this.scalar * other.jCoefficient
      - this.iCoefficient * other.kCoefficient
      + this.jCoefficient * other.scalar
      + this.kCoefficient * other.iCoefficient,
  kCoefficient = this.scalar * other.kCoefficient
      + this.iCoefficient * other.jCoefficient
      - this.jCoefficient * other.iCoefficient
      + this.kCoefficient * other.scalar
)

operator fun Quaternion.times(scalar: Number): Quaternion = quaternion(
  scalar = scalar.toFloat() * this.scalar,
  iCoefficient = scalar.toFloat() * iCoefficient,
  jCoefficient = scalar.toFloat() * jCoefficient,
  kCoefficient = scalar.toFloat() * kCoefficient
)

operator fun Number.times(quaternion: Quaternion): Quaternion = quaternion * this

operator fun Quaternion.div(scalar: Number): Quaternion = quaternion(
  scalar = this.scalar / scalar.toFloat(),
  iCoefficient = iCoefficient / scalar.toFloat(),
  jCoefficient = jCoefficient / scalar.toFloat(),
  kCoefficient = kCoefficient / scalar.toFloat()
)

operator fun Number.div(quaternion: Quaternion): Quaternion = quaternion / this

operator fun Quaternion.unaryPlus(): Quaternion = this

operator fun Quaternion.unaryMinus(): Quaternion = quaternion(-scalar, -iCoefficient, -jCoefficient, -kCoefficient)

fun Quaternion.dotProduct(other: Quaternion): Float {
  return this.scalar * other.scalar + this.iCoefficient * other.iCoefficient + this.jCoefficient * other.jCoefficient +
      this.kCoefficient * other.kCoefficient
}

fun Quaternion.hamiltonProduct(other: Quaternion) = this * other

fun Quaternion.conjugate(): Quaternion = quaternion(scalar, -iCoefficient, -jCoefficient, -kCoefficient)

fun Quaternion.norm(): Float =
  sqrt(scalar * scalar + iCoefficient * iCoefficient + jCoefficient * jCoefficient + kCoefficient * kCoefficient)

fun Quaternion.normalize(): Quaternion = this / norm()

fun Quaternion.inverse(): Quaternion = conjugate() / norm()

fun Quaternion.negate(): Quaternion = -this

val identityQuaternion = quaternion(scalar = 1, iCoefficient = 0, jCoefficient = 0, kCoefficient = 0)

