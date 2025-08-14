package com.jackbradshaw.otter.demo.materials

import com.jackbradshaw.otter.demo.DemoScope
import com.jackbradshaw.otter.demo.config.Constants
import com.jackbradshaw.otter.engine.core.EngineCore
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import javax.inject.Inject
import kotlin.random.Random

@DemoScope
class MaterialsImpl
@Inject
internal constructor(private val engineCore: EngineCore, private val random: Random) : Materials {

  private var materials =
      List<Material>(Constants.ITEM_CHANNELS) {
        Material(engineCore.extractAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md").apply {
          setColor("Color", ColorRGBA.Green)
        }
      }

  override suspend fun getRandomly() = materials[random.nextInt(materials.size - 1)]
}
