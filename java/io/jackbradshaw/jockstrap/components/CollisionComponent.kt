package io.jackbradshaw.jockstrap.components

import com.jme3.bullet.collision.PhysicsCollisionObject
import io.jackbradshaw.jockstrap.elements.Component

interface CollisionComponent : Component<Pair<Node, PhysicsCollisionObject>> {
  fun collisions(): Flow<Pair<PhysicsCollisionObject>>
}
