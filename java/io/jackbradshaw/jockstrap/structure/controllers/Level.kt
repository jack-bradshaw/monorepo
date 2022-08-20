package io.jackbradshaw.jockstrap.structure.controllers

import io.jackbradshaw.jockstrap.structure.frames.Hostable
import io.jackbradshaw.jockstrap.structure.frames.Playable
import io.jackbradshaw.jockstrap.structure.frames.Restorable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.jockstrap.structure.controllers.Integration

interface Level : Hostable<Game>, Restorable<LevelSnapshot>, Playable {

  val id: LevelId

  fun components(): BinaryDeltaFlow<Component>

  suspend fun findIntegrationBy(id: IntegrationId): Integration?
  suspend fun findIntegrationBy(engineElement: Any): Integration?

  suspend fun findItemBy(id: ItemId): Item?
  suspend fun findItemBy(integration: Integration): Item?
  suspend fun findItemBy(engineElement: Any): Item?

  suspend fun findComponentBy(id: ComponentId): Component?
  suspend fun findComponentBy(nestedComponent: Component): Component?
  suspend fun findComponentBy(item: Item): Component?
  suspend fun findComponentBy(integration: Integration): Component?
  suspend fun findComponentBy(engineElement: Any): Component?
}