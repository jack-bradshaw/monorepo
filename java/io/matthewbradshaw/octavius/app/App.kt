package io.matthewbradshaw.octavius.app

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.octavius.core.Game
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.MainScope
import com.jme3.app.LostFocusBehavior

class App @Inject internal constructor(private val game: Game) : SimpleApplication() {

  init {
    setLostFocusBehavior(LostFocusBehavior.Disabled)
  }

  private val coroutineScope = MainScope()

  override fun simpleInitApp() {
    coroutineScope.launch{
      game
        .ui()
        .map { it.scene() }
        .collect {
          getRootNode().detachAllChildren()
          getRootNode().attachChild(it)
        }
      }
      start()
    }

  override fun simpleUpdate(tpf: Float) = runBlocking {
    game.ticker().tick(tpf)
  }
}