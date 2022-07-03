package io.matthewbradshaw.merovingian.engine

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.system.JmeContext
import com.jme3.app.VREnvironment
import com.jme3.bullet.BulletAppState
import com.jme3.system.AppSettings
import com.jme3.scene.Node
import io.matthewbradshaw.merovingian.MerovingianScope
import io.matthewbradshaw.merovingian.config.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@MerovingianScope
class EngineImpl @Inject internal constructor(
  private val config: Config
) : Engine, SimpleApplication() {

  private val started = MutableStateFlow(false)
  private var totalRuntimeSec = 0.0

  private val settings = AppSettings(/* loadDefaults= */ true)
  private val vr = if (config.vrEnabled) createVrAppState() else null
  private val physics = BulletAppState()

  private val coroutineScopeJob = Job()
  private val coroutineScope = CoroutineScope(coroutineScopeJob)

  private val frameworkNode = Node("framework").also { getRootNode().attachChild(it) }
  private val applicationNode = Node("application").also { getRootNode().attachChild(it) }

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
      if (vr != null) stateManager.attach(vr)
      stateManager.attach(physics)
      if (config.headlessEnabled) start(JmeContext.Type.Headless) else start()
      started.filter { it == true }.first()
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

  override fun extractCamera() = cam
  override fun extractAssetManager() = assetManager
  override fun extractApp() = this
  override fun extractVr() = vr
  override fun extractPhysics() = physics
  override fun extractStateManager() = stateManager
  override fun extractFrameworkNode() = frameworkNode
  override fun extractApplicationNode() = applicationNode
  override fun extractCoroutineScope(): CoroutineScope = coroutineScope
  override fun extractTotalTime(): Double = totalRuntimeSec

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}



