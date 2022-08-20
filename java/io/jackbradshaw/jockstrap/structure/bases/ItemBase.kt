package io.jackbradshaw.jockstrap.structure.bases

import io.jackbradshaw.klu.flow.BinaryDelta
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.klu.flow.BinaryDeltaPair
import io.jackbradshaw.jockstrap.physics.placeZero
import io.jackbradshaw.jockstrap.physics.Placement
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import io.jackbradshaw.jockstrap.structure.controllers.Item
import io.jackbradshaw.jockstrap.structure.controllers.ItemId

/**
 * A convenience implementation of [Item] that does all the heavy lifting. This class implements many of the interface
 * functions which reduces the work on the end-engineer but takes away some control. Engineers who need access to these
 * functions can instead override the pre* and post* functions to receive callbacks when the functions enter and exit.
 */
abstract class ItemBase(override val id: ItemId) : Item {

  /*private val placement = MutableStateFlow<Transform>(originTransform())
  private val attachedComponents = mutableMapOf<EntityId, Entity>()
  private val exportedComponents = mutableSetOf<ExportedComponent<*>>()
  private val attachedComponentFlow = MutableSharedFlow<BinaryDeltaPair<Entity>>(replay = 0)
  private val exportedComponentFlow = MutableSharedFlow<BinaryDeltaPair<ExportedComponent<*>>>(replay = 0)

  final override fun placement() = placement

  final override suspend fun place(placement: Transform) {
    prePlace(placement)
    attachedComponents.forEach { TODO() }
    exportedComponents.forEach { TODO() }
    postPlace(placement)
  }

  final override suspend fun findEntity(id: EntityId, searchImmediateDescendantsOnly: Boolean): Entity? {
    preFindEntity(id, searchImmediateDescendantsOnly)
    val descendant = attachedComponents[id] ?: if (searchImmediateDescendantsOnly) null else findInDescendants(id)
    postFindEntity(id, searchImmediateDescendantsOnly, descendant)
    return descendant
  }

  final override suspend fun attachedDescendants() = attachedComponents.values.toSet()

  final override fun attachDescendantFlow(): BinaryDeltaFlow<Entity> = attachedComponentFlow.onStart {
    attachedComponents.values.forEach { attachedComponentFlow.tryEmit(it to BinaryDelta.INCLUDE) }
  }

  final override suspend fun exportedComponents() = exportedComponents

  final override fun exportedComponentFlow(): BinaryDeltaFlow<ExportedComponent<*>> = exportedComponentFlow.onStart {
    exportedComponents.forEach { exportedComponentFlow.tryEmit(it to BinaryDelta.INCLUDE) }
  }

  final override suspend fun onAttachToHost() {
    preOnAttachToHost()
    TODO()
    postOnAttachToHost()
  }

  final override suspend fun onDetachFromHost() {
    preOnDetachFromHost()
    TODO()
    postOnDetachFromHost()
  }

  protected suspend fun attach(entity: Entity) {
    preAttach(entity)
    if (entity.containsKey(entity.id)) {
      throw IllegalStateException("An entity with ID ${entity.id} is already attached.")
    } else {
      attachedComponents[entity.id] = entity
      attachedComponentFlow.tryEmit(entity to BinaryDelta.INCLUDE)
    }
    postAttach(entity)
  }

  protected suspend fun attach(entities: Iterable<Entity>) {
    entities.forEach { attach(it) }
  }

  protected suspend fun detach(entity: Entity) {
    preDetach(entity)
    attachedComponents.remove(entity.id)
    attachedComponentFlow.tryEmit(entity to BinaryDelta.EXCLUDE)
    postDetach(entity)
  }

  protected suspend fun detach(entities: Iterable<Entity>) {
    entities.forEach { detach(it) }
  }

  protected suspend fun export(component: ExportedComponent<*>) {
    exportedComponents.add(component)
    exportedComponentFlow.tryEmit(component to BinaryDelta.INCLUDE)
  }

  protected suspend fun export(components: Iterable<ExportedComponent<*>>) {
    components.forEach { export(it) }
  }

  protected suspend fun unexport(component: ExportedComponent<*>) {
    if (!exportedComponents.contains(component)) return
    exportedComponents.remove(component)
    exportedComponentFlow.tryEmit(component to BinaryDelta.EXCLUDE)
  }

  protected suspend fun unexport(components: Iterable<ExportedComponent<*>>) {
    components.forEach { unexport(it) }
  }

  protected suspend fun prePlace(transform: Transform) = Unit

  protected suspend fun postPlace(transform: Transform) = Unit

  protected suspend fun preFindEntity(id: EntityId, searchImmediateDescendantsOnly: Boolean) = Unit

  protected suspend fun postFindEntity(id: EntityId, searchImmediateDescendantsOnly: Boolean, result: Entity) = Unit

  protected suspend fun preOnAttachToHost() = Unit

  protected suspend fun postOnAttachToHost() = Unit

  protected suspend fun preOnDetachFromHost() = Unit

  protected suspend fun postOnDetachFromHost() = Unit*/
}