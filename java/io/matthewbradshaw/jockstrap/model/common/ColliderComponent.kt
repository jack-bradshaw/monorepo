package java.io.matthewbradshaw.jockstrap.model.common

import io.matthewbradshaw.jockstrap.model.elements.Component
import io.matthewbradshaw.jockstrap.model.elements.Entity

class CollisionComponent(
  override val id: ComponentId,
  override val source: Entity,
  override val item: PhysicsCollisionObject,
  override val onAttach: suspend () -> Unit = {},
  override val onDetach: suspend () -> Unit = {}
) : Component<PhysicsCollisionObject>()