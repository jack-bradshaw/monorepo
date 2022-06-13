package io.matthewbradshaw.experimental.jmonkeyengine

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.system.AppSettings
import javax.inject.Inject

class App : SimpleApplication() {
  override fun simpleInitApp() {
    val box = Box(1f, 1f, 1f)
    val geometry = Geometry("Box", box)
    val material = Material(
        assetManager,
	"Common/MatDefs/Misc/Unshaded.j3md"
    )

    material.setColor("Color", ColorRGBA.Blue)
    geometry.setMaterial(material)

    rootNode.attachChild(geometry)
  }

  override fun simpleUpdate(tpf: Float) {
    // TODO
  }
}
