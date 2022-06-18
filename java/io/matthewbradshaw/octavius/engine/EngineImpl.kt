package io.matthewbradshaw.octavius.engine

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.octavius.Game
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import com.jme3.scene.Spatial
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flatMapLatest
import io.matthewbradshaw.kmonkey.coroutines.dispatcher
import com.jme3.app.LostFocusBehavior
import io.matthewbradshaw.octavius.ticker.Ticker
import io.matthewbradshaw.octavius.OctaviusScope
import com.jme3.app.state.AppState
import java.util.concurrent.atomic.AtomicBoolean
import com.jme3.app.VRAppState

@OctaviusScope
class EngineImpl(
  private val ticker: Ticker,
  appStates: List<AppState>
) : Engine, SimpleApplication(*appStates.toTypedArray()) {

  private val scope = CoroutineScope(dispatcher())

  private val vrAppState: VRAppState? = appStates.filter { it::class == VRAppState::class }.firstOrNull() as VRAppState?

  private val gameFlow = MutableStateFlow<Game?>(null)

  init {
    if (vrAppState != null) setLostFocusBehavior(LostFocusBehavior.Disabled)
  }

  override fun simpleInitApp() {
    scope.launch {
      gameFlow
        .flatMapLatest { it?.ui() ?: flowOf<Spatial?>(null) }
        .collect {
          getRootNode().detachAllChildren()
          it?.let { getRootNode().attachChild(it) }
        }
    }
  }

  override fun simpleUpdate(tpf: Float) = runBlocking {
    ticker.tick(tpf)
  }

  private var started = AtomicBoolean(false)
  override suspend fun play(game: Game) {
    gameFlow.value = game
    if (started.compareAndSet(false, true)) start()
  }

  override fun camera() = cam
  override fun assetManager() = assetManager
  override fun root() = this
  override fun vr() = vrAppState
}