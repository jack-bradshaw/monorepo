package io.matthewbradshaw.jockstrap.model.components

import io.matthewbradshaw.jockstrap.sensation.Color
import io.matthewbradshaw.klu.flow.NiceFlower
import io.matthewbradshaw.

interface LightingComponent : Component {
  abstract val color: NiceFlower<Color>
  abstract val behavior: NiceFlower<LightingComponentBehavior>
}