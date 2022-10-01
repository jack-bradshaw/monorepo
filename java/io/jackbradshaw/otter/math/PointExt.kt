package io.jackbradshaw.otter.math

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Creates a vector set to ([x], [y], [z]).
 */
fun point(x: Number = 0f, y: Number = 0f, z: Number = 0f): Point =
    Point.newBuilder().setX(x.toFloat()).setY(y.toFloat()).setZ(z.toFloat()).build()

/**
 * Creates a new vector by adding this vector to [other].
 */
operator fun Point.plus(other: Point): Point = point(x + other.x, y + other.y, z + other.z)

operator fun Point.plus(vector: Vector): Point = point(x + vector.x, y + vector.y, z + vector.z)

/**
 * Creates a new vector by subtracting [other] from this vector.
 */
operator fun Point.minus(other: Point): Point = point(x - other.x, y - other.y, z - other.z)

/**
 * Creates a new vector by multiplying each component of this vector by [scalar].
 */
operator fun Point.times(scalar: Number): Point =
    point(x * scalar.toFloat(), y * scalar.toFloat(), z * scalar.toFloat())

/**
 * Creates a new vector by multiplying this number by each component of [vector].
 */
operator fun Number.times(vector: Point): Point = vector * this

/**
 * Creates a new vector by dividing each component of this vector by [scalar].
 */
operator fun Point.div(scalar: Number): Point = (1f / scalar.toFloat()) * this

/**
 * Creates a new vector by multiplying this number by each component of [vector].
 */
operator fun Number.div(vector: Point): Point = vector / this

/**
 * Essentially a no-op. Returns this vector as is.
 */
operator fun Point.unaryPlus(): Point = this

/**
 * Creates a new vector by negating this vector (i.e multiplying it by -1)
 */
operator fun Point.unaryMinus(): Point = -1 * this

fun Point.distanceTo(other: Point) = sqrt((other.x - x).pow(2) + (other.y - y).pow(2) + (other.z - z).pow(2))

/**
 * A point at the origin: (0, 0, 0).
 */
val originPoint = point()
