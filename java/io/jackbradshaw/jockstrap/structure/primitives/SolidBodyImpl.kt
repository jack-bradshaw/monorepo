package io.jackbradshaw.jockstrap.structure.primitives

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.scene.Node
import io.jackbradshaw.jockstrap.elements.ComponentId

class SolidBodyImpl(
    override val id: ComponentId,
    override val source: io.jackbradshaw.jockstrap.structure.controllers.Item,
) : io.jackbradshaw.jockstrap.structure.bases.BasePrimitive<Pair<Node, PhysicsCollisionObject>>(), io.jackbradshaw.jockstrap.structure.primitives.SolidBody {
  override fun intrinsic() = TODO()
  override fun collisions() = TODO()
}
