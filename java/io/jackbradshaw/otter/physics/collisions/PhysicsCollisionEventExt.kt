package io.jackbradshaw.otter.physics.collisions

import com.jme3.bullet.collision.PhysicsCollisionEvent

fun PhysicsCollisionEvent.toCollision() =
    Collision(
        getNodeA(),
        getNodeB(),
        interaction(getCombinedFriction(), getCombinedRestitution(), getAppliedImpulse()))
