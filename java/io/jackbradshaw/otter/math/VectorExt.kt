package io.jackbradshaw.otter.math

import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

fun vector(origin: Point = originPoint, destination: Point = originPoint) =
    Vector.newBuilder().setX(destination.x - origin.x).setY(destination.y - origin.y).setZ(destination.z - origin.z)
        .build()

fun vector(destination: Point = originPoint) = vector(originPoint, destination)

fun vector(x: Float = 0f, y: Float = 0f, z: Float = 0f) = vector(point(x, y, z))

operator fun Vector.plus(other: Vector): Vector = vector(x + other.x, y + other.y, z + other.z)

operator fun Vector.minus(other: Vector): Vector = vector(x - other.x, y - other.y, z - other.z)

operator fun Vector.times(scalar: Number): Vector =
    vector(scalar.toFloat() * x, scalar.toFloat() * y, scalar.toFloat() * z)

operator fun Number.times(vector: Vector): Vector = vector * this

operator fun Vector.div(scalar: Number): Vector =
    vector(x / scalar.toFloat(), y / scalar.toFloat(), z / scalar.toFloat())

operator fun Number.div(vector: Vector): Vector = vector / this

fun Vector.perElementProduct(other: Vector): Vector = vector(
    x = x * other.x,
    y = y * other.y,
    z = z * other.z
)

/**
 * Calculates the cross product (https://en.wikipedia.org/wiki/Cross_product) of this vector with [other].
 */
fun Vector.crossProduct(other: Point): Vector = vector(
    x = (y * other.z) - (z * other.y),
    y = (z * other.x) - (x * other.z),
    z = (x * other.y) - (y * other.x),
)

/**
 * Calculates the dot product (https://en.wikipedia.org/wiki/Dot_product) of this vector with [other].
 */
fun Vector.dotProduct(other: Vector): Float = (x * other.x) + (y * other.y) + (z * other.z)

/**
 * Calculates the length of this vector.
 */
fun Vector.length(): Float = sqrt(x.pow(2) + y.pow(2) + z.pow(2)).toFloat()

/**
 * Creates a new vector that is the normalized form of this vector (https://en.wikipedia.org/wiki/Unit_vector).
 */
fun Vector.normalize(): Vector = this / length()

/**
 * Calculates the projection (https://en.wikipedia.org/wiki/Vector_projection) of this vector onto [other]
 */
fun Vector.projectionOnto(other: Vector): Vector = (this.dotProduct(other) * other) / other.length().pow(2)

/**
 * Calculates the angle between this vector and [other]. The result is measured in radians.
 */
fun Vector.angleTo(other: Vector): Float = acos(this.dotProduct(other) / (this.length() * other.length()))

/**
 * A vector with zero length.
 */
val zeroVector = vector()

val unitXVector = vector(x = 1f)

val unitYVector = vector(y = 1f)

val unitZVector = vector(z = 1f)