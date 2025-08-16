package com.jackbradshaw.otter.physics.model

import com.jackbradshaw.otter.math.model.Point
import com.jackbradshaw.otter.math.model.Quaternion
import com.jackbradshaw.otter.math.model.Vector
import com.jackbradshaw.otter.math.model.identityQuaternion
import com.jackbradshaw.otter.math.model.minus
import com.jackbradshaw.otter.math.model.originPoint
import com.jackbradshaw.otter.math.model.perElementProduct
import com.jackbradshaw.otter.math.model.plus
import com.jackbradshaw.otter.math.model.times
import com.jackbradshaw.otter.math.model.toJMonkeyQuaternion
import com.jackbradshaw.otter.math.model.toJMonkeyVector
import com.jackbradshaw.otter.math.model.toOtterPoint
import com.jackbradshaw.otter.math.model.toOtterQuaternion
import com.jackbradshaw.otter.math.model.toOtterVector
import com.jackbradshaw.otter.math.model.zeroVector
import com.jme3.math.Transform as JmeTransform

fun placement(
    position: Point = originPoint,
    rotation: Quaternion = identityQuaternion,
    scale: Vector = zeroVector
): Placement =
    Placement.newBuilder().setPosition(position).setRotation(rotation).setScale(scale).build()

fun Placement.relativeTo(placement: Placement): Placement =
    placement(
        position = position - placement.position,
        rotation = rotation - placement.rotation,
        scale = scale.perElementProduct(placement.scale))

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
    JmeTransform(
        position.toJMonkeyVector(), rotation.toJMonkeyQuaternion(), scale.toJMonkeyVector())

/** Creates a new Merovingian Transform which is equivalent to this vector. */
fun JmeTransform.toOtterPlacement() =
    placement(
        getTranslation().toOtterPoint(),
        getRotation().toOtterQuaternion(),
        getScale().toOtterVector())

val placeZero = placement()
