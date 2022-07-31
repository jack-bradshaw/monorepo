package io.matthewbradshaw.jockstrap.model.components

import io.matthewbradshaw.jockstrap.model.elements.Component
import io.matthewbradshaw.jockstrap.model.elements.Entity
import io.matthewbradshaw.jockstrap.model.elements.ComponentId
import com.jme3.bullet.collision.PhysicsCollisionObject

class ColliderComponent(
  override val id: ComponentId,
  override val source: Entity,
  override val intrinsic: PhysicsCollisionObject
) : Component<PhysicsCollisionObject, ColliderComponentSnapshot>
