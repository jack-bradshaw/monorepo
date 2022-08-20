package io.jackbradshaw.jockstrap.structure.bases

import io.jackbradshaw.jockstrap.engine.Engine
import io.jackbradshaw.jockstrap.structure.controllers.Level
import io.jackbradshaw.jockstrap.structure.controllers.LevelId

/**
 * A convenience implementation of [Level] that does all the heavy lifting. This class implements many of the interface
 * functions which reduces the work on the end-engineer but takes away some control. Engineers who need access to these
 * functions can instead override the pre* and post* functions to receive callbacks when the functions enter and exit.
 */
abstract class BaseLevel(
    private val engine: Engine,
) : Level {

  /*private val rootNode = Node("level_root")

  private val entitiesById = mutableMapOf<EntityId, Entity>()

  private val entitiesByCollider = mutableMapOf<PhysicsCollisionObject, Entity>()

  private val entitiesByLight = mutableMapOf<Light, Entity>()

  private val entitiesToCollectionJobs = mutableMapOf<Entity, Job>()

  private var ongoingOperations: Job?

  private var canSnapshot: Boolean

  override suspend fun findEntityById(id: EntityId): Entity? {
    preFindEntityById(id)
    return entitiesById[id].also { postFindEntityById(id, it) }
  }

  override suspend fun findEntityWithCollider(collider: PhysicsCollisionObject): Entity {
    preFindEntityWithCollider()
    return entitiesByCollider[collider].also { postFindEntityWithCollider(collider, it) }
  }

  override suspend fun findEntityWithLight(light: Light): Entity {
    preFindEntityWithLight(light)
    return entitiesByLight[light].also { postFindEntityWithLight(light, it) }
  }

  override suspend fun onAttachToHost() {
    preOnAttachToHost()
    engine.extractGameNode().attachChild(rootNode)
    beginOngoingOperations()
    postOnAttachToHost()
  }

  override suspend fun onDetachFromHost() {
    preOnDetachFromHost()
    engine.extractGameNode().removeChild(rootNode)
    cancelOngoingOperations()
    postOnDetachFromHost()
  }

  override suspend fun prepareForSnapshot(): Boolean {
    prePrepareForSnapshot()
    return TODO().also { postPrepareForSnapshot() }
  }

  override suspend fun takeSnapshot(): LevelSnapshot {
    preTakeSnapshot()
    return TODO().also {
      postTakeSnapshot(it)
    }
  }

  override suspend fun onPlace(snapshot: PlacementSnapshot? = null) {
    preOnPlace()
    entitiesById.values.forEach {
      it.onPlace
    }
    postOnPlace()
  }

  override suspend fun onPresimulate() {
    preOnPresimulate()
    TODO()
    postOnPresimulate()
  }

  override suspend fun onSimulate(snapshot: SimulationSnapshot? = null) {
    preOnSimulate(snapshot)
    TODO()
    postOnSimulate(snapshot)
  }

  suspend fun attach(entity: Entity) {
    preAttach(entity)
    if (entitiesById.containsKey(entity.id)) {
      throw IllegalArgumentException("Cannot attach $entity. An entity with ID '${entity.id}' is already attached.")
    }
    entitiesById[entity.id] = entity
    TODO() // setup handling for descendants
    TODO() // setup handling for exported components
    entity.onAttachToHost()
    postAttach(entity)
  }

  suspend fun attach(entities: Iterable<Entity>) {
    entities.forEach { attach(it) }
  }

  suspend fun detach(entity: Entity) {
    preDetach(entity)
    if (entitiesById.containsKey(entity.id)) {
      TODO() // cancel handling for descendants
      TODO() // cancel handling for exported components
    }
    postDetach(entity)
  }

  suspend fun detach(entities: Iterable<Entity>) {
    entities.forEach { detach(it) }
  }

  suspend fun preFindEntityById(id: EntityId) = Unit
  suspend fun postFindEntityById(id: EntityId, result: Entity?) = Unit
  suspend fun preFindEntityWithCollider(collider: PhysicsCollisionObject) = Unit
  suspend fun postFindEntityWithCollider(collider: PhysicsCollisionObject, result: Entity?) = Unit
  suspend fun preFindEntityWithLight(light: Light) = Unit
  suspend fun postFindEntityWithLight(light: Light, result: Entity?) = Unit
  suspend fun preOnAttachToHost() = Unit
  suspend fun postOnAttachToHost() = Unit
  suspend fun preOnDetachFromHost() = Unit
  suspend fun postOnDetachFromHost() = Unit
  suspend fun prePrepareForSnapshot() = Unit
  suspend fun postPrepareForSnapshot(result: Boolean) = Unit
  suspend fun preTakeSnapshot() = Unit
  suspend fun postTakeSnapshot(result: LevelSnapshot) = Unit
  suspend fun preOnPlace(snapshot: PlacementSnapshot?) = Unit
  suspend fun postOnPlace(snapshot: PlacementSnapshot?) = Unit
  suspend fun preOnPresimulate() = Unit
  suspend fun postOnPresimulate() = Unit
  suspend fun preOnSimulate(snapshot: SimulationSnapshot?) = Unit
  suspend fun postOnSimulate(snapshot: SimulationSnapshot?) = Unit
  suspend fun preAttach(entity: Entity) = Unit
  suspend fun postAttach(entity: Entity) = Unit
  suspend fun preDetach(entity: Entity) = Unit
  suspend fun postDetach(entity: Entity) = Unit

  private suspend fun beginOngoingOperations() {
    ongoingOperations = launch {
      TODO()
    }
  }

  private suspend fun cancelOngoingOperations() {
    ongoingOperations?.cancel()
  }*/
}
/*
sealed interface RestorationBehavior {
  object StartFresh : RestorationBehavior
  class RestoreSnapshotWithId(val id: LevelSnapshotId) : RestorationBehavior
  class RestoreSnapshot(val snapshot: LevelSnapshot) : RestorationBehavior
}

sealed class SwitchResult(val isSuccess: Boolean) {
  object Done : SwitchResult(true)
  class NoSnapshotFound(val id: LevelSnapshotId) : SwitchResult(false)
  class GenericFailure(val error: Throwable) : SwitchResult(false)
}

sealed class SaveResult(val isSuccess: Boolean) {
  object Done : SaveResult(true)
  object NoLevelSet : SaveResult(false)
  object SnapshotUnavailable : SaveResult(false)
  class GenericFailure(val error: Throwable) : SaveResult(false)
}*/