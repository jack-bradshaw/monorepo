package io.matthewbradshaw.octavius.jmonkey

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.octavius.core.Game
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jme3.renderer.Camera
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.MainScope
import com.jme3.app.LostFocusBehavior
import io.matthewbradshaw.octavius.heartbeat.Ticker

class JMonkeyAppImpl @Inject internal constructor(private val game: Game, private val ticker: Ticker) :
  JMonkeyApp, SimpleApplication() {

  init {
    setLostFocusBehavior(LostFocusBehavior.Disabled)
  }

  private val coroutineScope = MainScope()

  override fun simpleInitApp() {
    coroutineScope.launch {
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
    ticker.tick(tpf)
  }

  override fun extractCamera() = cam
  override fun extractAssetManager() = assetManager

  override fun asSimpleApplication() = this as SimpleApplication
}