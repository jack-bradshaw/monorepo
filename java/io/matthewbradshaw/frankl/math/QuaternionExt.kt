package io.matthewbradshaw.frankl.model.math

import com.jme3.math.Quaternion as JmeQuaternion

fun quaternion(w: Number, x: Number, y: Number, z: Number) =
  Quaternion.newBuilder().setW(w.toFloat()).setX(x.toFloat()).setY(y.toFloat()).setZ(z.toFloat()).build()

private val IDENTITY = quaternion(x = 0, y = 0, z = 0, w = 1)

fun identity() = IDENTITY

/**
 * Creates a new JMonkey Engine 3 Quaternion which is equivalent to this.
 */
fun Quaternion.toJme3(): JmeQuaternion = JmeQuaternion(x, y, z, w)

/**
 * Creates a new Merovingian Quaternion which is equivalent to this.
 */
fun JmeQuaternion.toMerv(): Quaternion = quaternion(w, x, y, z)

operator fun Quaternion.plus(other: Quaternion): Quaternion = toJme3().add(other.toJme3()).toMerv()


fun Quaternion.interpolate(other: Quaternion, interpolationFactor: Float): Quaternion =
  JmeQuaternion(toJme3(), other.toJme3(), interpolationFactor).toMerv()

// TODO other methods