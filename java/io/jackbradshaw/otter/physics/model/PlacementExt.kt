package io.jackbradshaw.otter.physics.model

import com.jme3.math.Transform as JmeTransform
import io.jackbradshaw.otter.math.model.Point
import io.jackbradshaw.otter.math.model.Quaternion
import io.jackbradshaw.otter.math.model.Vector
import io.jackbradshaw.otter.math.model.identityQuaternion
import io.jackbradshaw.otter.math.model.originPoint
import io.jackbradshaw.otter.math.model.perElementProduct
import io.jackbradshaw.otter.math.model.plus
import io.jackbradshaw.otter.math.model.minus
import io.jackbradshaw.otter.math.model.times
import io.jackbradshaw.otter.math.model.toJMonkeyQuaternion
import io.jackbradshaw.otter.math.model.toJMonkeyVector
import io.jackbradshaw.otter.math.model.toOtterPoint
import io.jackbradshaw.otter.math.model.toOtterQuaternion
import io.jackbradshaw.otter.math.model.toOtterVector
import io.jackbradshaw.otter.math.model.zeroVector

fun placement(
  position: Point = originPoint,
  rotation: Quaternion = identityQuaternion,
  scale: Vector = zeroVector
): Placement =
  Placement.newBuilder().setPosition(position).setRotation(rotation).setScale(scale).build()

operator fun Placement.minus(placement: Placement): Placement = placement(
    position = position - placement.position,
    rotation = rotation - placement.rotation,
    scale = scale.perElementProduct(placement.scale)
)

fun Placement.moveTo(position: Point): Placement = toBuilder().setPosition(position).build()

fun Placement.moveBy(displacement: Vector): Placement =
  toBuilder().setPosition(position + displacement).build()

fun Placement.rotateTo(rotation: Quaternion): Placement = toBuilder().setRotation(rotation).build()

fun Placement.rotateBy(rotation: Quaternion): Placement =
  toBuilder().setRotation(this.rotation * rotation).build()

fun Placement.scaleTo(scale: Vector): Placement = toBuilder().setScale(scale).build()

fun Placement.scaleBy(scale: Vector): Placement =
  toBuilder().setScale(this.scale.perElementProduct(scale)).build()

/** Creates a new JMonkey Engine 3 Transform which is equivalent to this vector. */
fun Placement.toJMonkeyTransform() =
  JmeTransform(position.toJMonkeyVector(), rotation.toJMonkeyQuaternion(), scale.toJMonkeyVector())

/** Creates a new Merovingian Transform which is equivalent to this vector. */
fun JmeTransform.toOtterPlacement() =
  placement(
    getTranslation().toOtterPoint(),
    getRotation().toOtterQuaternion(),
    getScale().toOtterVector()
  )

val placeZero = placement()
