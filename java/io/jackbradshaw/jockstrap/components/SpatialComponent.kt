package io.jackbradshaw.jockstrap.components

import com.jme3.scene.Spatial
import io.jackbradshaw.jockstrap.elements.Component
import io.jackbradshaw.klu.flow.Flower

interface SpatialComponent : Component<Spatial> {
  val spatial: Flower<Spatial>
}