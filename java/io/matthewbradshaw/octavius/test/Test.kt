package io.matthewbradshaw.octavius.test

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import org.junit.Before
import com.jme3.app.state.AppState
import com.jme3.system.AppSettings
import org.junit.Test
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.app.VRAppState
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.coroutines.EmptyCoroutineContext
import com.google.common.truth.Truth.assertThat

class TestApplication : SimpleApplication() {

  init {
    setSettings(AppSettings( /* loadDefaults= */ true))
    setShowSettings(false)
  }

  lateinit var mainThread: Thread

  override fun simpleInitApp() {
    mainThread = Thread.currentThread()
    rootNode.attachChild(Geometry("Box", Box(1f, 1f, 1f)))
  }

  override fun simpleUpdate(tpf: Float) {
    /* Interact with game events in the main loop */
  }
}