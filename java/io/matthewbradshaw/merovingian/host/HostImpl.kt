package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.engine.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import io.matthewbradshaw.merovingian.model.Game
import io.matthewbradshaw.merovingian.engine.RootNode
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import com.jme3.scene.Node
import io.matthewbradshaw.merovingian.model.GameItem
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.coroutineScope
import com.google.auto.factory.Provided
import com.google.auto.factory.AutoFactory
import kotlinx.coroutines.withContext

@AutoFactory(className = "HostFactory")
class HostImpl(
  private val game: Game,
  @Provided @RootNode private val rootNode: Node,
  @Provided @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : Host {

  override suspend fun go() {
    coroutineScope {
      withContext(Dispatchers.Default) {
        var currentItem: GameItem? = null
        game
          .representation()
          .flowOn(mainDispatcher)
          .onEach {
            currentItem?.let { rootNode.detachChild(it.representation()) }
            currentItem = it
            rootNode.attachChild(it.representation())
          }.collect()
      }

      launch(Dispatchers.Default) {
        game.logic()
      }
    }
  }
}