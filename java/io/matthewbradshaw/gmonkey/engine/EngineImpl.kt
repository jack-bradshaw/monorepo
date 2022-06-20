package io.matthewbradshaw.gmonkey.engine

import com.jme3.app.SimpleApplication
import com.jme3.app.VREnvironment
import com.jme3.app.VRConstants
import com.jme3.app.LostFocusBehavior
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.gmonkey.ui.Item
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.cancel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import io.matthewbradshaw.gmonkey.coroutines.dispatcher
import io.matthewbradshaw.gmonkey.ticker.Ticker
import io.matthewbradshaw.gmonkey.GMonkeyScope
import com.jme3.app.VRAppState
import com.jme3.system.AppSettings
import io.matthewbradshaw.gmonkey.config.Paradigm

@GMonkeyScope
class EngineImpl @Inject internal constructor(
  private val ticker: Ticker,
  private val paradigm: Paradigm
) : Engine, SimpleApplication() {

  private val scope = CoroutineScope(dispatcher())
  private var vrAppState: VRAppState? = null
  private val game: MutableStateFlow<Item?>(null)

  init {
    when (paradigm) {
      Paradigm.FLATWARE -> initForFlatware()
      Paradigm.VR -> initForVr()
    }
  }

  private fun initForFlatware() {
    val settings = AppSettings( /* loadDefaults= */ true)
    setSettings(settings)
    setShowSettings(false)
    vrAppState = null
  }

  private fun initForVr() {
    val settings = AppSettings(/* loadDefaults= */ true).apply {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
    }
    setSettings(settings)
    setShowSettings(false)

    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not initialize.")
    }
    vrAppState = VRAppState(settings, environment).apply {
      setMirrorWindowSize(
        DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX,
        DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX
      )
    }
    stateManager.attach(vrAppState)

    setLostFocusBehavior(LostFocusBehavior.Disabled)
  }

  override fun simpleInitApp() {
    scope.launch {
      game
        .ui()
        .collect {
          getRootNode().detachAllChildren()
          getRootNode().attachChild(it)
        }
    }
  }

  override fun simpleUpdate(tpf: Float) = runBlocking {
    ticker.tick(tpf)
  }

  override fun destroy() {
    super.destroy()
    scope.cancel()
  }


  override fun play(game: Item) {
    TODO("Not yet implemented")
  }

  override fun extractCamera() = cam
  override fun extractAssetManager() = assetManager
  override fun extractApp() = this
  override fun extractVr() = vrAppState

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}