package io.matthewbradshaw.jockstrap.components

import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.light.Light
import com.jme3.light.PointLight
import com.jme3.light.SpotLight
import io.matthewbradshaw.jockstrap.math.toJMonkeyVector
import io.matthewbradshaw.jockstrap.bases.BaseComponent
import io.matthewbradshaw.jockstrap.components.AmbientConfig
import io.matthewbradshaw.jockstrap.components.PointConfig
import io.matthewbradshaw.jockstrap.components.SpotConfig
import io.matthewbradshaw.jockstrap.elements.ComponentId
import io.matthewbradshaw.jockstrap.elements.ComponentSnapshot
import io.matthewbradshaw.jockstrap.elements.Entity
import io.matthewbradshaw.jockstrap.physics.Placement
import io.matthewbradshaw.jockstrap.sensation.Color
import io.matthewbradshaw.jockstrap.sensation.toJMonkeyColor
import io.matthewbradshaw.jockstrap.sensation.white
import io.matthewbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface CollisionComponentImpl : BaseComponent<Pair<Node, PhysicsCollisionObject>>, CollisionComponent {

}
