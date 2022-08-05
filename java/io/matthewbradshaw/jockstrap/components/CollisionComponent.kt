package io.matthewbradshaw.jockstrap.components

import com.jme3.bullet.collision.PhysicsCollisionObject
import io.matthewbradshaw.jockstrap.elements.Component

interface CollisionComponent : Component<Pair<Node, PhysicsCollisionObject>> {
  fun collisions(): Flow<Pair<PhysicsCollisionObject>>
}
