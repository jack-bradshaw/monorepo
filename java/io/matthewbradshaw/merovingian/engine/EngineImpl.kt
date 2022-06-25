package io.matthewbradshaw.merovingian.engine

import com.jme3.app.SimpleApplication
import com.jme3.app.VREnvironment
import com.jme3.app.VRConstants
import com.jme3.app.LostFocusBehavior
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import io.matthewbradshaw.merovingian.MerovingianScope
import com.jme3.app.VRAppState
import com.jme3.system.AppSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import io.matthewbradshaw.merovingian.config.Paradigm

@MerovingianScope
class EngineImpl @Inject internal constructor(
  private val paradigm: Paradigm
) : Engine, SimpleApplication() {

  private val started = MutableStateFlow(false)

  private var vrAppState: VRAppState? = null

  private val coroutineScopeJob = Job()
  private val coroutineScope = CoroutineScope(coroutineScopeJob)

  private lateinit var settings: AppSettings

  private var totalTime = 0.0

  init {
    when (paradigm) {
      Paradigm.FLATWARE -> initForFlatware()
      Paradigm.VR -> initForVr()
    }

    runBlocking {
      start()
      started.filter { it == true }.first()
    }
  }

  private fun initForFlatware() {
    settings = AppSettings( /* loadDefaults= */ true)
    setSettings(settings)
    setShowSettings(false)
    vrAppState = null
  }

  private fun initForVr() {
    settings = AppSettings(/* loadDefaults= */ true).apply {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
    }
    setSettings(settings)
    setShowSettings(false)

    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not successfully initialize")
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
    started.value = true
  }



  override fun simpleUpdate(tpf: Float) = runBlocking {
    totalTime = totalTime + tpf
  }

  override fun destroy() {
    coroutineScopeJob.cancel()
    super.destroy()
  }

  override fun extractCamera() = cam
  override fun extractAssetManager() = assetManager
  override fun extractApp() = this
  override fun extractVr() = vrAppState
  override fun extractRootNode() = getRootNode()
  override fun extractCoroutineScope(): CoroutineScope = coroutineScope
  override fun extractTotalTime(): Double = totalTime
  override fun extractFrameRate(): Int = settings.getFrameRate()

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}



