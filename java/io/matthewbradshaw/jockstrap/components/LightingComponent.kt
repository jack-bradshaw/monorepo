package io.matthewbradshaw.jockstrap.components

import com.jme3.light.Light
import io.matthewbradshaw.jockstrap.elements.Component
import io.matthewbradshaw.jockstrap.sensation.Color
import io.matthewbradshaw.klu.flow.Flower

interface LightingComponent : Component<Light> {
  abstract val color: Flower<Color>
  abstract val behavior: Flower<LightingComponentBehavior>
}