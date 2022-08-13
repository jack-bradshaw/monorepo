package io.jackbradshaw.jockstrap.model.components

import com.jme3.bullet.collision.PhysicsCollisionObject
import io.jackbradshaw.jockstrap.model.elements.Component
import com.jme3.scene.Node
import kotlinx.coroutines.flow.Flow

interface RigidBodyComponent : io.jackbradshaw.jockstrap.model.elements.Component<Pair<Node, PhysicsCollisionObject>> {
  fun collisions(): Flow<Pair<Node, PhysicsCollisionObject>>
  fun controller(): RigidBodyControl
}
