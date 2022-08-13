package io.jackbradshaw.jockstrap.model.components

import io.jackbradshaw.jockstrap.model.elements.Entity

class BipedComponentImpl(
        override val id: ComponentId,
        override val source: io.jackbradshaw.jockstrap.model.elements.Entity,
) : BaseComponent<Light>(), io.jackbradshaw.jockstrap.model.components.LightingComponent