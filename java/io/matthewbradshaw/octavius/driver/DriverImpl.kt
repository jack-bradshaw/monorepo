package io.matthewbradshaw.octavius.driver

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.octavius.core.Game
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import com.jme3.scene.Spatial
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.MainScope
import com.jme3.app.LostFocusBehavior
import io.matthewbradshaw.octavius.ignition.Ignition
import io.matthewbradshaw.octavius.ticker.Ticker

class DriverImpl @Inject internal constructor(private val ignition: Ignition, private val ticker: Ticker) :
  Driver, SimpleApplication() {

  private val gameFlow = MutableStateFlow<Game?>(null)

  init {
    setLostFocusBehavior(LostFocusBehavior.Disabled)
  }

  private val mainScope = MainScope()

  override fun simpleInitApp() {
    mainScope.launch {
      gameFlow
        .flatMapLatest { it?.ui() ?: flowOf<Spatial?>(null) }
        .collect {
          getRootNode().detachAllChildren()
          it?.let { getRootNode().attachChild(it) }
        }
    }
    mainScope.launch {
      ignition.started().first()
      start()
    }
  }

  override fun simpleUpdate(tpf: Float) = runBlocking {
    ticker.tick(tpf)
  }

  override suspend fun play(game: Game) {
    gameFlow.emit(game)
  }

  override  fun extractCamera() = cam
  override  fun extractAssetManager() = assetManager
  override fun extractApplication() = this
}