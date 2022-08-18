package java.io.jackbradshaw.jockstrap.bases

import io.jackbradshaw.klu.flow.BinaryDelta
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.klu.flow.BinaryDeltaPair
import io.jackbradshaw.jockstrap.physics.originTransform
import io.jackbradshaw.jockstrap.physics.Transform
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import java.io.jackbradshaw.jockstrap.elements.Entity

/**
 * A convenience implementation of [Entity] that does all the heavy lifting. Entity can be difficult to implement
 * correctly, so the framework authors recommend using this class as the base for all production entities.
 */
open class BaseItem(override val id: EntityId) : Entity {

  private val placement = MutableStateFlow<Transform>(originTransform())
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

  protected suspend fun postOnDetachFromHost() = Unit
}