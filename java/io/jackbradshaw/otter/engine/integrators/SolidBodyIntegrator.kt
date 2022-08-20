package java.io.jackbradshaw.otter.engine.integrators

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.scene.Node
import kotlinx.coroutines.flow.Flow
import java.io.jackbradshaw.otter.structure.controllers.Integration

interface SolidBodyIntegrator : Integration<Pair<Node, PhysicsCollisionObject>> {
  fun collisions(): Flow<Pair<Node, PhysicsCollisionObject>>
  fun controller(): RigidBodyControl
}
