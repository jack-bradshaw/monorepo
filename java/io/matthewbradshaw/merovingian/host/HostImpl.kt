package io.matthewbradshaw.merovingian.host

import com.jme3.scene.Spatial
import io.matthewbradshaw.klu.flow.BinaryDelta
import io.matthewbradshaw.merovingian.coroutines.physicsDispatcher
import io.matthewbradshaw.merovingian.coroutines.renderingDispatcher
import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.model.Game
import io.matthewbradshaw.merovingian.model.LevelItem
import io.matthewbradshaw.merovingian.model.LevelSnapshot
import io.matthewbradshaw.merovingian.model.LevelSnapshotId
import io.matthewbradshaw.merovingian.snapshot.SnapshotStore
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.withLock
import io.matthewbradshaw.klu.concurrency.once
import io.matthewbradshaw.klu.concurrency.errorOnSubsequentRuns
import kotlinx.coroutines.withContext

class HostImpl(
  private val engine: Engine,
  private val snapshotStore: SnapshotStore
) : Host {

  private lateinit var game: Game<*>

  private var trainMan: Job? = null

  private val playGame = once {
    game.onAttachedToHost()
    setupFlows()
  }.errorOnSubsequentRuns("Host can only run one game per instance. To run a different game, create a new host.")

  override suspend fun play(game: Game<*>) {
    this.game = game
    playGame()
  }

  override suspend fun stop() {
    trainMan?.cancel()
    engine.extractApp().stop()
  }

  private suspend fun setupFlows() {
    withContext(Dispatchers.Default) {
      trainMan = game
        .levelReloadedOrChanged()
        .map { game.decodeLevelKey(snapshot.metadata.id.literal) }
        .onEach {
          val level = game.createCleanLevel(it)
          level.onAttachedToHost()
          level.onAttachToHost(snapshot)
        }
        .flowOn(engine.renderingDispatcher())
        .collect()
      //currentCoordinator = item.coordinator.also { engine.extractRootNode().attachChild(it) }
    }

    /*withContext(engine.physicsDispatcher()) {
      currentColliderProcessor = launch {
        item.exportedColliders().collect {
          val collider = it.first
          val delta = it.second
          when (delta) {
            BinaryDelta.INCLUDE -> engine.extractPhysics().getPhysicsSpace().add(collider)
            BinaryDelta.EXCLUDE -> engine.extractPhysics().getPhysicsSpace().remove(collider)
          }
        }
      }
    }

    withContext(engine.renderingDispatcher()) {
      currentLightingProcessor = launch {
        item.exportedLighting().collect {
          val light = it.first
          val delta = it.second
          when (delta) {
            BinaryDelta.INCLUDE -> engine.extractRootNode().addLight(light)
            BinaryDelta.EXCLUDE -> engine.extractRootNode().removeLight(light)
          }
        }
      }
    }*/
  }
}