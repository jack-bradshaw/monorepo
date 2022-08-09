package io.jackbradshaw.jockstrap.components

import com.jme3.light.Light
import io.jackbradshaw.jockstrap.elements.Component
import io.jackbradshaw.jockstrap.sensation.Color
import io.jackbradshaw.klu.flow.Flower

interface LightingComponent : Component<Light> {
  abstract val color: Flower<Color>
  abstract val behavior: Flower<LightingComponentBehavior>
}