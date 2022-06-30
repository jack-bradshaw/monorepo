package io.matthewbradshaw.merovingian.host

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.engine.EngineBound
import io.matthewbradshaw.merovingian.model.WorldItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AutoFactory(className = "HostFactory")
class HostImpl(
  private val worldItem: WorldItem,
  @Provided private val engine: Engine,
  @Provided @EngineBound private val engineDispatcher: CoroutineDispatcher,
  @Provided @EngineBound private val engineScope: CoroutineScope,
) : Host {

  override suspend fun go() {
    engineScope.launch {
      engine.extractRootNode().attachChild(worldItem.representation())
      worldItem.logic()
    }
  }
}