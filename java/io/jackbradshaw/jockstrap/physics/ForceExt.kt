package io.jackbradshaw.jockstrap.physics

import io.jackbradshaw.jockstrap.math.originPoint
import io.jackbradshaw.jockstrap.math.zeroVector
import io.jackbradshaw.jockstrap.math.Point
import io.jackbradshaw.jockstrap.math.Vector

fun force(force: Vector = zeroVector, location: Point = originPoint): Force =
  Force.newBuilder().setForce(force).setLocation(location).build()
