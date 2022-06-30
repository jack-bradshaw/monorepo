package io.matthewbradshaw.merovingian.demo.items

import com.jme3.scene.Geometry
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.matthewbradshaw.klu.concurrency.once
import kotlin.random.Random
import io.matthewbradshaw.merovingian.demo.materials.Materials
import io.matthewbradshaw.merovingian.demo.DemoScope
import javax.inject.Inject

@DemoScope
class CubeImpl @Inject internal constructor(
  private val materials: Materials,
  private val random: Random,
) : Cube {

  private val size = random.nextFloat()
  private lateinit var cube: Spatial

  private val preparations = once {
    cube = Geometry("cube_box", Box(size, size, size)).apply {
      setMaterial(materials.getRandomly())
    }
    number = number + 1
  }

  override suspend fun representation(): Spatial {
    preparations.runIfNeverRun()
    return cube
  }

  companion object {
    var number = 0
  }
}

