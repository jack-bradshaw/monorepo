package io.matthewbradshaw.jockstrap.physics

import io.matthewbradshaw.jockstrap.math.Point
import io.matthewbradshaw.jockstrap.math.Quaternion
import io.matthewbradshaw.jockstrap.math.Vector
import io.matthewbradshaw.jockstrap.math.originPoint
import io.matthewbradshaw.jockstrap.math.perElementProduct
import io.matthewbradshaw.jockstrap.math.zeroVector
import io.matthewbradshaw.jockstrap.math.identityQuaternion
import io.matthewbradshaw.jockstrap.math.plus
import io.matthewbradshaw.jockstrap.math.times

fun statics(
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