package io.jackbradshaw.jockstrap.demo.materials

import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import io.jackbradshaw.jockstrap.demo.DemoScope
import io.jackbradshaw.jockstrap.demo.config.Constants
import io.jackbradshaw.jockstrap.engine.Engine
import javax.inject.Inject
import kotlin.random.Random

@DemoScope
class MaterialsImpl @Inject internal constructor(
  private val engine: Engine,
  private val random: Random
) : Materials {

  private var materials = List<Material>(Constants.ITEM_CHANNELS) {
    Material(
      engine.extractAssetManager(),
      "Common/MatDefs/Misc/Unshaded.j3md"
    ).apply {
      setColor("Color", ColorRGBA.Green)
    }
  }

  override suspend fun getRandomly() = materials[random.nextInt(materials.size - 1)]
}