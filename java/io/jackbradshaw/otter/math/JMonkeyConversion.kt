package io.jackbradshaw.otter.math

import com.jme3.math.Quaternion as JmeQuaternion
import com.jme3.math.Vector3f as JmeVector

fun Quaternion.toJMonkeyQuaternion(): JmeQuaternion = JmeQuaternion(iCoefficient, jCoefficient, kCoefficient, scalar)

fun JmeQuaternion.tootterQuaternion(): Quaternion =
  quaternion(scalar = w, iCoefficient = x, jCoefficient = y, kCoefficient = z)

fun Vector.toJMonkeyVector(): JmeVector = JmeVector(x, y, z)

fun JmeVector.tootterVector(): Vector = vector(point(x, y, z))

fun Point.toJMonkeyVector(): JmeVector = JmeVector(x, y, z)

fun JmeVector.tootterPoint(): Point = point(x, y, z)