package io.jackbradshaw.otter.physics.collisions

import com.jme3.scene.Spatial

data class Collision(
    val body1: Spatial,
    val body2: Spatial,
    val interation: Interaction
)