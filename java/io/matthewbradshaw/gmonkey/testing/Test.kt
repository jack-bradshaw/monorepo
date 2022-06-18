package io.matthewbradshaw.gmonkey.testing

import com.jme3.app.SimpleApplication
import com.jme3.system.AppSettings
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box

class Test : SimpleApplication() {

  init {
    setSettings(AppSettings( /* loadDefaults= */ true))
    setShowSettings(true)
  }

  lateinit var mainThread: Thread

  override fun simpleInitApp() {
    start()
    //mainThread = Thread.currentThread()
    rootNode.attachChild(Geometry("Box", Box(1f, 1f, 1f)))
  }

  override fun simpleUpdate(tpf: Float) {
    /* Interact with game events in the main loop */
    println("hi")
  }
}