package io.matthewbradshaw.jockstrap.model.components

import io.matthewbradshaw.jockstrap.model.elements.Component
import io.matthewbradshaw.jockstrap.model.elements.Entity
import io.matthewbradshaw.jockstrap.physics.Placement
import io.matthewbradshaw.jockstrap.model.elements.ComponentId
import io.matthewbradshaw.jockstrap.model.bases.BaseComponent
import io.matthewbradshaw.jockstrap.physics.toJMonkeyTransform
import io.matthewbradshaw.jockstrap.physics.placeZero
import kotlinx.coroutines.flow.first
import com.jme3.scene.Spatial

class SpatialComponent(
  override val id: ComponentId,
  override val source: Entity,
  override val intrinsic: Spatial
) : BaseComponent<Spatial, SpatialComponentSnapshot>() {

  override suspend fun contributeToPlace(placement: Placement) {
    intrinsic.setLocalTransform(placement.toJMonkeyTransform())
  }
}