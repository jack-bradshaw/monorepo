package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.engine.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import io.matthewbradshaw.merovingian.engine.RootNode
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import com.jme3.scene.Node
import io.matthewbradshaw.merovingian.model.GameItem
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.coroutineScope
import com.google.auto.factory.Provided
import com.google.auto.factory.AutoFactory
import kotlinx.coroutines.join
import kotlinx.coroutines.withContext

@AutoFactory(className = "HostFactory")
class HostImpl(
  private val gameItem: GameItem,
  @Provided @RootNode private val rootNode: Node,
  @Provided @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : Host {

  override suspend fun go() {
    coroutineScope {
      withContext(Dispatchers.Default) {
        launch(mainDispatcher) { gameItem.prepare() }.join()
        launch(mainDispatcher) { rootNode.attachChild(gameItem.representation()) }
        //launch(Dispatchers.Default) { gameItem.logic() }
      }
    }
  }
}