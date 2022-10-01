package io.jackbradshaw.otter.physics

import io.jackbradshaw.otter.math.Point
import io.jackbradshaw.otter.math.Vector
import io.jackbradshaw.otter.math.originPoint
import io.jackbradshaw.otter.math.zeroVector

fun force(force: Vector = zeroVector, location: Point = originPoint): Force =
    Force.newBuilder().setForce(force).setLocation(location).build()
