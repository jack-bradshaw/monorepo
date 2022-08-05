package io.matthewbradshaw.jockstrap.components

import com.jme3.scene.Spatial
import io.matthewbradshaw.jockstrap.elements.Component
import io.matthewbradshaw.klu.flow.Flower

interface SpatialComponent : Component<Spatial> {
  val spatial: Flower<Spatial>
}