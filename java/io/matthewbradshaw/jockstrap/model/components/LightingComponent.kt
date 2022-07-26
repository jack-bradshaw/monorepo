package io.matthewbradshaw.jockstrap.model.components

import io.matthewbradshaw.jockstrap.model.elements.Component
import io.matthewbradshaw.jockstrap.model.elements.Entity

class LightingComponent(
  override val id: ComponentId,
  override val source: Entity,
  override val item: Light,
  override val onAttach: suspend () -> Unit = {},
  override val onDetach: suspend () -> Unit = {}
) : Component<Light>()