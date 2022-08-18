package io.jackbradshaw.jockstrap.structure.primitives

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.scene.Node
import kotlinx.coroutines.flow.Flow

interface SolidBody : io.jackbradshaw.jockstrap.structure.controllers.Primitive<Pair<Node, PhysicsCollisionObject>> {
  fun collisions(): Flow<Pair<Node, PhysicsCollisionObject>>
  fun controller(): RigidBodyControl
}
