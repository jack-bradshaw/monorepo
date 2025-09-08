package com.jackbradshaw.otter.physics.model

import com.jackbradshaw.otter.math.model.Point
import com.jackbradshaw.otter.math.model.Vector
import com.jackbradshaw.otter.math.model.originPoint
import com.jackbradshaw.otter.math.model.zeroVector

fun force(force: Vector = zeroVector, location: Point = originPoint): Force =
    Force.newBuilder().setForce(force).setLocation(location).build()
