package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.model.WorldItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class HostImpl @Inject internal constructor(
  private val engine: Engine,
) : Host {

  private var logic: Job? = null

  override suspend fun run(item: WorldItem) {
    engine.extractCoroutineScope().launch {
      logic?.let { it.cancel() }
      engine.extractRootNode().detachAllChildren()
      engine.extractRootNode().attachChild(item.representation())
      logic = launch { item.logic() }
    }
  }
}