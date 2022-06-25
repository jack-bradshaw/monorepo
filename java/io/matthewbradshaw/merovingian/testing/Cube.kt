package io.matthewbradshaw.merovingian.testing

import com.jme3.scene.Geometry
import com.jme3.scene.Spatial
import com.jme3.material.Material
import io.matthewbradshaw.merovingian.model.GameItem
import com.jme3.scene.shape.Box
import io.matthewbradshaw.kotty.once
import com.google.auto.factory.Provided
import kotlin.random.Random
import com.google.auto.factory.AutoFactory

@TestingScope
@AutoFactory
class Cube(
  private val material: Material,
  @Provided private val random: Random,
) : GameItem {

  private lateinit var cube: Spatial

  private val size = random.nextFloat()


  override suspend fun representation(): Spatial {
    preparations.runOnce()
    return cube
  }

  private val preparations = once {
    cube = Geometry("cube_box", Box(size, size, size)).apply {
      setMaterial(this@Cube.material)
    }
    number = number + 1
  }

  companion object {
    private const val SIZE = 1f
    var number = 0
  }
}

