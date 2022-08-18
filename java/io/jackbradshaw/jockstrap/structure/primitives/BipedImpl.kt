package io.jackbradshaw.jockstrap.structure.primitives

class BipedImpl(
    override val id: ComponentId,
    override val source: io.jackbradshaw.jockstrap.structure.controllers.Item,
) : BaseComponent<Light>(), Light