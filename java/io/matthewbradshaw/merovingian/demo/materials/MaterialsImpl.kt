package io.matthewbradshaw.merovingian.demo.materials

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import kotlin.random.Random
import io.matthewbradshaw.merovingian.demo.DemoScope
import io.matthewbradshaw.merovingian.demo.config.Config
import javax.inject.Inject

@DemoScope
class MaterialsImpl @Inject internal constructor(
  private val assetManager: AssetManager,
  private val random: Random
) : Materials {

  private var materials = List<Material>(Config.ITEM_CHANNELS) {
    Material(
      assetManager,
      "Common/MatDefs/Misc/Unshaded.j3md"
    ).apply {
      setColor("Color", ColorRGBA.Green)
    }
  }

  override suspend fun getRandomly() = materials[random.nextInt(materials.size - 1)]
}