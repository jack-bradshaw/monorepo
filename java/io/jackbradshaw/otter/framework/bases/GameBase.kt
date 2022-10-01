package io.jackbradshaw.otter.structure.bases

import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.structure.controllers.Game

/**
 * A convenience implementation of [Game] that does all the heavy lifting. This class implements
 * many of the interface functions which reduces the work on the end-engineer but takes away some
 * control. Engineers who need access to these functions can instead override the pre* and post*
 * functions to receive callbacks when the functions enter and exit.
 */
abstract class GameBase(
    private val engine: Engine,
) : Game {

  /*abstract suspend fun createDefaultLevel(): Level

  abstract suspend fun createCleanLevel(levelId: LevelId): Level

  private val levelGuard = Mutex()

  private val level = MutableStateFlow<Level?>(null)

  private val startOperation = once {
    preStart()
    switchLevel(createDefaultLevel(), LoadBehavior.CreateFresh)
    postStart()
  }.errorOnSubsequentRuns("Game can only be started once.")

  override suspend fun start() {
    startOperation()
  }

  override suspend fun isStarted() = startOperation.hasRun()

  override fun level() = level

  private suspend fun doSwitchToLevel(levelId: LevelId, restoration: RestorationBehavior): SwitchResult {
    level = createCleanLevel(levelId)
    try {
      val snapshot = when (restoration) {
        RestorationBehavior.None -> null
        RestorationBehavior.RestoreSnapshotById -> {
          snapshotStore.loadSnapshot(restoration.id) ?: return SwitchResult.NoSnapshotFound(restoration.id)
        }
        RestorationBehavior.RestoreSnapshot -> restoration.snapshot
      }
      level.start(snapshot)
      return SwitchResult.Done
    } catch (e: Exception) {
      return SwitchResult.GenericFailure(e)
    }
  }

  private suspend fun Level.stop() {
    onDetachFromHost()
  }

  private suspend fun Level.start(snapshot: LevelSnapshot) {
    onAttachToHost()
    if (snapshot == null) {
      level.onPlace()
      level.onPresimulate()
      level.onSimulate()
    } else {
      level.onPlace(snapshot.placement)
      // Never presimulate during restoration as this would create a discontinuity between save and load for the player
      level.onSimulate(snapshot.simulation)
    }
  }

  private suspend fun Level.save(): SaveResult {
    return try {
      val canSnapshot = prepareForSnapshot()
      if (canSnapshot) {
        snapshotStore.saveSnapshot(takeSnapshot())
        java.io.jackbradshaw.otter.elements.SaveResult.Done
      } else {
        java.io.jackbradshaw.otter.elements.SaveResult.SnapshotUnavailable
      }
    } catch (e: Exception) {
      java.io.jackbradshaw.otter.elements.SaveResult.GenericFailure(e)
    }
  }

  protected suspend fun preStart() = Unit

  protected suspend fun postStart() = Unit

  protected suspend fun preSwitchToLevel(levelId: LevelId, restorationBehavior: RestorationBehavior) = Unit

  protected suspend fun postSwitchToLevel(levelId: LevelId, restorationBehavior: RestorationBehavior, result: SwitchResult) = Unit

  protected suspend fun preSaveCurrentLevel() = Unit

  protected suspend fun postSaveCurrentLevel() = Unit

  protected suspend fun switchToLevel(levelId: LevelId, restorationBehavior: RestorationBehavior): SwitchResult {
    levelGuard.withLock {
      preSwitchToLevel(levelId, restorationBehavior)
      return doSwitchToLevel(levelId, restorationBehavior).also { postSwitchToLevel(levelId, restorationBehavior, it) }
    }
  }

  protected suspend fun saveCurrentLevel(): SaveResult {
    levelGuard.withLock {
      return level?.save() ?: SaveResult.NoLevelSet
    }
  }*/
}
