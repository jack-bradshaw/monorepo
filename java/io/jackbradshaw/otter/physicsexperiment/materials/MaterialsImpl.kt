package io.jackbradshaw.otter.physics.experiment.materials

import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.physics.experiment.PhysicsExperimentScope
import javax.inject.Inject

@PhysicsExperimentScope
class MaterialsImpl @Inject internal constructor(
    private val engine: Engine,
) : Materials {

  private val red by lazy {
    Material(
        engine.extractAssetManager(),
        "Common/MatDefs/Misc/Unshaded.j3md"
    ).apply {
      setColor("Color", ColorRGBA.Red)
    }
  }

  private val blue by lazy {
    Material(
        engine.extractAssetManager(),
        "Common/MatDefs/Misc/Unshaded.j3md"
    ).apply {
      setColor("Color", ColorRGBA.Blue)
    }
  }

  private val green by lazy {
    Material(
        engine.extractAssetManager(),
        "Common/MatDefs/Misc/Unshaded.j3md"
    ).apply {
      setColor("Color", ColorRGBA.Green)
    }
  }

  override suspend fun getRed() = red
  override suspend fun getBlue() = blue
  override suspend fun getGreen() = green
}