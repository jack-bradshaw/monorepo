package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.model.WorldItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class HostImpl @Inject internal constructor(
  private val engine: Engine,
) : Host {

  private val mutex = Mutex()
  private var activeLogic: Job? = null

  override suspend fun run(item: WorldItem) {
    mutex.withLock {
      engine.extractCoroutineScope().launch(engine.extractCoroutineDispatcher()) {
        activeLogic?.let { it.cancel() }
        engine.extractRootNode().detachAllChildren()
        engine.extractRootNode().attachChild(item.representation())
        activeLogic = launch { item.logic() }
      }
    }
  }
}