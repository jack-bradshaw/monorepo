package io.matthewbradshaw.jockstrap.components

import com.jme3.scene.Node
import com.jme3.scene.Spatial
import io.matthewbradshaw.jockstrap.bases.BaseComponent
import io.matthewbradshaw.jockstrap.elements.ComponentId
import io.matthewbradshaw.jockstrap.elements.Entity
import io.matthewbradshaw.jockstrap.physics.Placement
import io.matthewbradshaw.jockstrap.physics.toJMonkeyTransform
import io.matthewbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SpatialComponentImpl(
  override val id: ComponentId,
  override val source: Entity,
) : BaseComponent<Spatial>(), SpatialComponent {

  private val guard = Mutex()

  override val spatial = NiceFlower<Spatial>(Node("default_for_component_id_$id")) {
    guard.withLock {
      it.placeAt(placement.get())
    }
  }

  override fun intrinsic() = spatial.asFlow()

  protected override suspend fun placeIntrinsic(placement: Placement) {
    guard.withLock {
      spatial.get().placeAt(placement)
    }
  }

  private suspend fun Spatial.placeAt(placement: Placement) {
    setLocalTransform(placement.toJMonkeyTransform())
  }
}