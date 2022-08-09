package io.jackbradshaw.jockstrap.physics

import io.jackbradshaw.jockstrap.math.Point
import io.jackbradshaw.jockstrap.math.Quaternion
import io.jackbradshaw.jockstrap.math.Vector
import io.jackbradshaw.jockstrap.math.originPoint
import io.jackbradshaw.jockstrap.math.perElementProduct
import io.jackbradshaw.jockstrap.math.zeroVector
import io.jackbradshaw.jockstrap.math.identityQuaternion
import io.jackbradshaw.jockstrap.math.plus
import io.jackbradshaw.jockstrap.math.times

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