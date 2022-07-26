package io.matthewbradshaw.jockstrap.physics

import io.matthewbradshaw.jockstrap.math.originPoint
import io.matthewbradshaw.jockstrap.math.zeroVector
import io.matthewbradshaw.jockstrap.math.Point
import io.matthewbradshaw.jockstrap.math.Vector

fun force(force: Vector = zeroVector, location: Point = originPoint): Force =
  Force.newBuilder().setForce(force).setLocation(location).build()
