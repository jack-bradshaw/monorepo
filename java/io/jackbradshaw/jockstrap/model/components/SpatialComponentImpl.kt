package io.jackbradshaw.jockstrap.model.components

import com.jme3.scene.Node
import com.jme3.scene.Spatial
import io.jackbradshaw.jockstrap.model.bases.BaseComponent
import io.jackbradshaw.jockstrap.elements.ComponentId
import io.jackbradshaw.jockstrap.model.elements.Entity
import io.jackbradshaw.jockstrap.physics.Placement
import io.jackbradshaw.jockstrap.physics.toJMonkeyTransform
import io.jackbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SpatialComponentImpl(
        override val id: ComponentId,
        override val source: io.jackbradshaw.jockstrap.model.elements.Entity,
) : io.jackbradshaw.jockstrap.model.bases.BaseComponent<Spatial>(), io.jackbradshaw.jockstrap.model.components.SpatialComponent {

  private val guard = Mutex()

  override val spatial = NiceFlower<Spatial>(Node("default_id_for_component_id_$id")) {
    guard.withLock {
      it.placeAt(placement.get())
    }
  }

  override fun intrinsic() = spatial.asFlow()

  override suspend fun placeIntrinsic(placement: Placement) {
    guard.withLock {
      spatial.get().placeAt(placement)
    }
  }

  private suspend fun Spatial.placeAt(placement: Placement) {
    setLocalTransform(placement.toJMonkeyTransform())
  }
}