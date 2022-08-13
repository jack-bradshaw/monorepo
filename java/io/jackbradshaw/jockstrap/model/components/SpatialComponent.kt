package io.jackbradshaw.jockstrap.model.components

import com.jme3.scene.Spatial
import io.jackbradshaw.jockstrap.model.elements.Component
import io.jackbradshaw.klu.flow.Flower

interface SpatialComponent : io.jackbradshaw.jockstrap.model.elements.Component<Spatial> {
  val spatial: Flower<Spatial>
}
