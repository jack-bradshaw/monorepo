package io.matthewbradshaw.merovingian.model.math

import com.jme3.math.Vector3f
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.math.abs
import kotlin.sequences.sequenceOf

/**
 * Creates a vector set to ([x], [y], [z]).
 */
fun vector3(x: Number, y: Number, z: Number): Vector3 =
  Vector3.newBuilder().setX(x.toFloat()).setY(y.toFloat()).setZ(z.toFloat()).build()

/**
 * Creates a vector set to ([x], 0, 0).
 */
fun vector3x(x: Number): Vector3 = vector3(x, 0, 0)

/**
 * Creates a vector set to (0, [y], 0).
 */
fun vector3y(y: Number): Vector3 = vector3(0, y, 0)

/**
 * Creates a vector set to (0, 0, [z]).
 */
fun vector3z(z: Number): Vector3 = vector3(0, 0, z)

private val ZERO = vector3(0, 0, 0)

/**
 * Gets a vector set to (0, 0, 0). The same instance is returned each time.
 */
fun zero(): Vector3 = ZERO

private val UNIT_X = vector3(1, 0, 0)

/**
 * Gets a vector set to (1, 0, 0). The same instance is returned each time.
 */
fun unitX(): Vector3 = UNIT_X

private val UNIT_Y = vector3(0, 1, 0)

/**
 * Gets a vector set to (0, 1, 0). The same instance is returned each time.
 */
fun unitY(): Vector3 = UNIT_Y

private val UNIT_Z = vector3(0, 0, 1)

/**
 * Gets a vector set to (0, 0, 1). The same instance is returned each time.
 */
fun unitZ(): Vector3 = UNIT_Z

/**
 * Creates a new JMonkey Engine 3 [Vector3f] which is equivalent to this vector.
 */
fun Vector3.toJme3(): Vector3f = Vector3f(x, y, z)

/**
 * Creates a new Merovingian [Vector3] which is equivalent to this vector.
 */
fun Vector3f.toMerv(): Vector3 = vector3(x, y, z)

/**
 * Creates a new vector by adding this vector to [other].
 */
operator fun Vector3.plus(other: Vector3): Vector3 = toJme3().add(other.toJme3()).toMerv()

/**
 * Creates a new vector by adding this vector to [other].
 */
operator fun Vector3.plus(other: Vector3f): Vector3 = toJme3().add(other).toMerv()

/**
 * Creates a new vector by adding [x], [y], and [z] to the x, y, and z components of this vector respectively.
 */
fun Vector3.plus(x: Number, y: Number, z: Number): Vector3 = this + vector3(x, y, z)

/**
 * Creates a new vector by adding [x] to the x component of this vector.
 */
fun Vector3.plusX(x: Number): Vector3 = this + vector3x(x)

/**
 * Creates a new vector by adding [y] to the y component of this vector.
 */
fun Vector3.plusY(y: Number): Vector3 = this + vector3y(y)

/**
 * Creates a new vector by adding [z] to the z component of this vector.
 */
fun Vector3.plusZ(z: Number): Vector3 = this + vector3z(z)

/**
 * Creates a new vector by subtracting [other] from this vector.
 */
operator fun Vector3.minus(other: Vector3): Vector3 = toJme3().subtract(other.toJme3()).toMerv()

/**
 * Creates a new vector by subtracting [other] from this vector.
 */
operator fun Vector3.minus(other: Vector3f): Vector3 = toJme3().subtract(other).toMerv()

/**
 * Creates a new vector by adding [x], [y], and [z] from the x, y, and z components of this vector respectively.
 */
fun Vector3.minus(x: Number, y: Number, z: Number): Vector3 = this - vector3(x, y, z)

/**
 * Creates a new vector by subtracting [x] to the x component of this vector.
 */
fun Vector3.minusX(x: Number): Vector3 = this - vector3x(x)

/**
 * Creates a new vector by adding [y] to the y component of this vector.
 */
fun Vector3.minusY(y: Number): Vector3 = this - vector3y(y)

/**
 * Creates a new vector by adding [z] to the z component of this vector.
 */
fun Vector3.minusZ(z: Number): Vector3 = this - vector3z(z)

/**
 * Creates a new vector by multiplying each component of this vector by [scalar].
 */
operator fun Vector3.times(scalar: Number): Vector3 = toJme3().mult(scalar.toFloat()).toMerv()

/**
 * Creates a new vector by multiplying this number by each component of [vector].
 */
operator fun Number.times(vector: Vector3): Vector3 = vector * this

/**
 * Creates a new vector by dividing each component of this vector by [scalar].
 */
operator fun Vector3.div(scalar: Number): Vector3 = toJme3().divide(scalar.toFloat()).toMerv()

/**
 * Creates a new vector by multiplying this number by each component of [vector].
 */
operator fun Number.div(vector: Vector3): Vector3 = vector / this

/**
 * Creates a new vector by negating this vector (i.e multiplying it by -1)
 */
operator fun Vector3.unaryMinus(): Vector3 = -1 * this

/**
 * Calculates the angle between this vector and [other]. The result is measured in radians.
 */
fun Vector3.angleBetween(other: Vector3): Float = toJme3().angleBetween(other.toJme3())

/**
 * Calculates the cross product (https://en.wikipedia.org/wiki/Cross_product) of this vector with [other].
 */
fun Vector3.crossProduct(other: Vector3): Vector3 = toJme3().cross(other.toJme3()).toMerv()

/**
 * Calculates the dot product (https://en.wikipedia.org/wiki/Dot_product) of this vector with [other].
 */
fun Vector3.dotProduct(other: Vector3): Float = toJme3().dot(other.toJme3())

/**
 * Returns whether the length of this vector is approximately 1 unit. The tolerance value defines the allowable error.
 * For example: A tolerance of 0.1 would cause any vector with length between 0.9 and 1.1 (inclusive) to return true.
 */
fun Vector3.isApproximatelyUnitLength(tolerance: Float): Boolean = abs(1 - length()) <= tolerance

/**
 * Returns whether this vector is approximately equal to [other]. The tolerance value defines the allowable error on a
 * per axis basis. For example: A tolerance of 0.1 and a vector of [1, 1, 1] would cause any vector with all x, y and z
 * coordinates between 0.9 and 1.1 to return true.
 */
fun Vector3.isApproximately(other: Vector3, perAxisTolerance: Float): Boolean {
  val xDeltaInTolerance = other.x - x <= perAxisTolerance
  val yDeltaInTolerance = other.y - y <= perAxisTolerance
  val zDeltaInTolerance = other.z - z <= perAxisTolerance
  return xDeltaInTolerance && yDeltaInTolerance && zDeltaInTolerance
}

/**
 * Calculates the length of this vector.
 */
fun Vector3.length(): Float = toJme3().length()

/**
 * Creates a new vector that is the normalized form of this vector (https://en.wikipedia.org/wiki/Unit_vector).
 */
fun Vector3.normalize(): Vector3 = toJme3().normalize().toMerv()

/**
 * Calculates the projection (https://en.wikipedia.org/wiki/Vector_projection) of this vector onto [other]
 */
fun Vector3.project(other: Vector3): Vector3 = toJme3().project(other.toJme3()).toMerv()

/**
 * Calculates the distance between the two vectors.
 */
fun Vector3.distance(other: Vector3): Float = toJme3().distance(other.toJme3())

/**
 * Gets the component corresponding to [i] where 0 -> x, 1 -> y, 2 -> z.
 *
 * @throws IndexOutOfBoundsException when i is not 0, 1 or 2
 */
operator fun Vector3.get(i: Int): Float = when (i) {
  0 -> x
  1 -> y
  2 -> z
  else -> throw IndexOutOfBoundsException("Index $i is not applicable to a 3D vector. Expected 0, 1 or 2.")
}

/**
 * Creates a new array containing the x, y, and z components of this vector (in that order).
 */
fun Vector3.toArray(): Array<Float> = arrayOf(get(0), get(1), get(2))

/**
 * Creates a new list containing the x, y, and z components of this vector (in that order).
 */
fun Vector3.toList(): List<Float> = listOf(get(0), get(1), get(2))

/**
 * Creates a new sequence containing the x, y, and z components of this vector (in that order).
 */
fun Vector3.asSequence(): Sequence<Float> = sequenceOf(get(0), get(1), get(2))

/**
 * Creates a new flow which emits the x, y, and z components of this vector (in that order) then terminates.
 */
fun Vector3.asFlow(): Flow<Float> = flowOf(get(0), get(1), get(2))