package io.matthewbradshaw.octavius.engine

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.octavius.core.Game
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import com.jme3.scene.Spatial
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import com.jme3.app.LostFocusBehavior
import io.matthewbradshaw.octavius.ignition.Ignition
import io.matthewbradshaw.octavius.ticker.Ticker
import io.matthewbradshaw.octavius.OctaviusScope
import com.jme3.app.state.AppState

@OctaviusScope
class EngineImpl(
  private val ticker: Ticker,
  private val ignition: Ignition,
  private val appStates: List<AppState>
) : Engine, SimpleApplication(*appStates.toTypedArray()) {

  private lateinit var mainThread: Thread
  private latienit var mainDispatcher: Dispatcher

  private val vrAppState = appStates.filter { it::class == VrAppState::class }.first()

  private val gameFlow = MutableStateFlow<Game?>(null)

  init {
    if (vrAppState != null) setLostFocusBehavior(LostFocusBehavior.Disabled)
  }

  override fun simpleInitApp() {
    mainThread = Thread.currentThread()
    coroutineScope.launch {
      gameFlow
              .onEach { println("gameflow emitted $it") }
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
  override fun application() = this
  override fun thread() = mainThread
  override fun dispatcher() = mainDispatcher
  override fun vr() = vrAppState
}