package io.matthewbradshaw.jockstrap.model.components

import io.matthewbradshaw.jockstrap.model.elements.Component
import io.matthewbradshaw.jockstrap.sensation.Color
import io.matthewbradshaw.klu.flow.NiceFlower
import com.jme3.light.Light

interface LightingComponent : Component<Light> {
  abstract val color: NiceFlower<Color>
  abstract val behavior: NiceFlower<LightingComponentBehavior>
}