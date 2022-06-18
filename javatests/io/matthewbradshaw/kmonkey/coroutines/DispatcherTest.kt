package io.matthewbradshaw.gmonkey.coroutines

import com.jme3.app.SimpleApplication
import org.junit.Before
import com.jme3.system.AppSettings
import org.junit.Test
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
import io.matthewbradshaw.gmonkey.octavius.engine.Paradigm
import io.matthewbradshaw.gmonkey.octavius.otto
import io.matthewbradshaw.gmonkey.testing.CubeGame

@RunWith(JUnit4::class)
class DispatcherTest {


  private lateinit var application: TestApplication
  private lateinit var dispatcher: JMonkeyDispatcher

  @Before
  fun before() {
    application = TestApplication()
    dispatcher = application.dispatcher()
  }

  @Test
  fun dispatcher_runsOnMainThread() {
    assertThat(Thread.currentThread()).isEqualTo(application.mainThread)
    /*runBlocking {
      launch(dispatcher) {

      }
    }*/
  }

  @Test
  fun dispatcher_dispatchesEachUnitOnce() {
    println("hi")
  }
}

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

fun startGame() {
  val octavius = otto(Paradigm.FLATWARE)
  val game = CubeGame(octavius)
  octavius.engine().play(game)
}