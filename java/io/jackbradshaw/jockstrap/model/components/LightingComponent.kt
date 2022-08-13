package io.jackbradshaw.jockstrap.model.components

import com.jme3.light.Light
import io.jackbradshaw.jockstrap.model.elements.Component
import io.jackbradshaw.jockstrap.graphics.Color
import io.jackbradshaw.klu.flow.Flower

interface LightingComponent : io.jackbradshaw.jockstrap.model.elements.Component<Light> {
  val color: Flower<Color>
  val behavior: Flower<LightingComponentBehavior>
}
