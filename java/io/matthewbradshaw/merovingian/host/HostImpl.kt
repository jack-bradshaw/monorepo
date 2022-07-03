package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.model.WorldItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import com.jme3.scene.Spatial
import io.matthewbradshaw.merovingian.coroutines.renderingDispatcher

class HostImpl @Inject internal constructor(
  private val engine: Engine,
) : Host {

  private val mutex = Mutex()
  private var activeRepresentation: Spatial? = null
  private var activeLogic: Job? = null


  override suspend fun run(item: WorldItem) {
    mutex.withLock {
      engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
        activeLogic?.let { it.cancel() }
        activeRepresentation?.let { engine.extractFrameworkNode().detachChild(it) }
        activeRepresentation = item.representation().also { engine.extractApplicationNode().attachChild(it) }
        activeLogic = launch { item.logic() }
      }
    }
  }
}