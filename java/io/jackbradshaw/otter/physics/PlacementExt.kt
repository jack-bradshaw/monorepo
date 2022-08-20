package io.jackbradshaw.otter.physics

import io.jackbradshaw.otter.math.Point
import io.jackbradshaw.otter.math.Quaternion
import io.jackbradshaw.otter.math.Vector
import io.jackbradshaw.otter.math.originPoint
import io.jackbradshaw.otter.math.perElementProduct
import io.jackbradshaw.otter.math.zeroVector
import io.jackbradshaw.otter.math.identityQuaternion
import io.jackbradshaw.otter.math.plus
import io.jackbradshaw.otter.math.times

fun placement(
  position: Point = originPoint,
  rotation: Quaternion = identityQuaternion,
  scale: Vector = zeroVector
): Placement = Placement.newBuilder().setPosition(position).setRotation(rotation).setScale(scale).build()

fun Placement.moveTo(position: Point): Placement = toBuilder().setPosition(position).build()

fun Placement.moveBy(displacement: Vector): Placement = toBuilder().setPosition(position + displacement).build()

fun Placement.rotateTo(rotation: Quaternion): Placement = toBuilder().setRotation(rotation).build()

fun Placement.rotateBy(rotation: Quaternion): Placement = toBuilder().setRotation(this.rotation * rotation).build()

fun Placement.scaleTo(scale: Vector): Placement = toBuilder().setScale(scale).build()

fun Placement.scaleBy(scale: Vector): Placement = toBuilder().setScale(this.scale.perElementProduct(scale)).build()

val placeZero = placement()