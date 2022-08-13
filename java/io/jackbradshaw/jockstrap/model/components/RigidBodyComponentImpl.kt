package io.jackbradshaw.jockstrap.model.components

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.scene.Node
import io.jackbradshaw.jockstrap.model.bases.BaseComponent
import io.jackbradshaw.jockstrap.elements.ComponentId
import io.jackbradshaw.jockstrap.model.elements.Entity

class RigidBodyComponentImpl(
        override val id: ComponentId,
        override val source: io.jackbradshaw.jockstrap.model.elements.Entity,
) : io.jackbradshaw.jockstrap.model.bases.BaseComponent<Pair<Node, PhysicsCollisionObject>>(), io.jackbradshaw.jockstrap.model.components.RigidBodyComponent {
    override fun intrinsic() = TODO()
    override fun collisions() = TODO()
}
