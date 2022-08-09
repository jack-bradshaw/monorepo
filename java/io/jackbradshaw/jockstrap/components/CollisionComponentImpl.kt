package io.jackbradshaw.jockstrap.components

import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.light.Light
import com.jme3.light.PointLight
import com.jme3.light.SpotLight
import io.jackbradshaw.jockstrap.math.toJMonkeyVector
import io.jackbradshaw.jockstrap.bases.BaseComponent
import io.jackbradshaw.jockstrap.components.AmbientConfig
import io.jackbradshaw.jockstrap.components.PointConfig
import io.jackbradshaw.jockstrap.components.SpotConfig
import io.jackbradshaw.jockstrap.elements.ComponentId
import io.jackbradshaw.jockstrap.elements.ComponentSnapshot
import io.jackbradshaw.jockstrap.elements.Entity
import io.jackbradshaw.jockstrap.physics.Placement
import io.jackbradshaw.jockstrap.sensation.Color
import io.jackbradshaw.jockstrap.sensation.toJMonkeyColor
import io.jackbradshaw.jockstrap.sensation.white
import io.jackbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface CollisionComponentImpl : BaseComponent<Pair<Node, PhysicsCollisionObject>>, CollisionComponent {

}
