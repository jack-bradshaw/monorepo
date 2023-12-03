package io.jackbradshaw.otter.physics.model

import io.jackbradshaw.otter.math.model.Point
import io.jackbradshaw.otter.math.model.Vector
import io.jackbradshaw.otter.math.model.originPoint
import io.jackbradshaw.otter.math.model.zeroVector

fun force(force: Vector = zeroVector, location: Point = originPoint): Force =
    Force.newBuilder().setForce(force).setLocation(location).build()
