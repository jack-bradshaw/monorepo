package io.jackbradshaw.otter.engine

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.app.VRConstants
import com.jme3.app.VREnvironment
import com.jme3.scene.Node
import com.jme3.bullet.BulletAppState
import com.jme3.audio.AudioRenderer
import com.jme3.system.AppSettings
import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.config.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import com.jme3.app.LostFocusBehavior
import kotlinx.coroutines.flow.first
import com.jme3.audio.Listener
import kotlinx.coroutines.runBlocking
import com.jme3.renderer.RenderManager
import javax.inject.Inject
import com.jme3.system.JmeContext
import com.jme3.system.Timer
import com.jme3.renderer.Renderer

@OtterScope
class EngineImpl @Inject internal constructor(
  private val config: Config
) : Engine, SimpleApplication() {

  private val started = MutableStateFlow(false)
  private var totalRuntimeSec = 0.0

  private val settings = AppSettings(/* loadDefaults= */ true).apply {
    if (config.vrEnabled) {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
      setGammaCorrection(config.gammaCorrectionEnabled)
    }
  }
  private val vr = if (config.vrEnabled) createVrAppState() else null
  private val physics = BulletAppState()

  private val coroutineScopeJob = Job()
  private val coroutineScope = CoroutineScope(coroutineScopeJob)

  private val frameworkNode = Node("framework_root")
  private val gameNode = Node("game_root")

  private fun createVrAppState(): VRAppState {
    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not successfully initialize")
    }
    return VRAppState(settings, environment).apply {
      setMirrorWindowSize(DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX, DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX)
    }
  }

  init {
    runBlocking {
      setSettings(settings)
      setShowSettings(false)
      setLostFocusBehavior(LostFocusBehavior.Disabled)
      if (config.headlessEnabled) start(JmeContext.Type.Headless) else start()
      started.filter { it == true }.first()
      if (vr != null) stateManager.attach(vr)
      stateManager.attach(physics)
      getRootNode().attachChild(frameworkNode)
      getRootNode().attachChild(gameNode)
    }
  }

  override fun simpleInitApp() {
    started.value = true
  }

  override fun simpleUpdate(tpf: Float) = runBlocking {
    totalRuntimeSec = totalRuntimeSec + tpf
  }

  override fun destroy() {
    coroutineScopeJob.cancel()
    super.destroy()
  }

  override fun extractApplication() = this
  override fun extractContext() = context
  override fun extractAssetManager() = assetManager
  override fun extractStateManager() = stateManager
  override fun extractInputManager() = inputManager
  override fun extractRenderManager() = renderManager
  override fun extractVideoRenderer() = renderer
  override fun extractAudioRenderer() = audioRenderer
  override fun extractDefaultInGameCamera() = cam
  override fun extractDefaultInGameMicrophone() = listener
  override fun extractDefaultViewPort() = viewPort
  override fun extractVr() = vr
  override fun extractPhysics() = physics
  override fun extractFrameworkNode() = frameworkNode
  override fun extractGameNode() = gameNode
  override fun extractCoroutineScope(): CoroutineScope = coroutineScope
  override fun extractTimer() = timer
  override fun extractTotalGameRuntime(): Double = totalRuntimeSec

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}



