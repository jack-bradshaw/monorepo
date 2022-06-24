package io.matthewbradshaw.merovingian.testing

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.jme3.scene.Geometry
import com.jme3.scene.Spatial
import com.jme3.material.Material
import io.matthewbradshaw.merovingian.model.GameItem
import com.jme3.scene.shape.Box
import com.google.auto.factory.Provided
import com.google.auto.factory.AutoFactory

@TestingScope
@AutoFactory
class Cube(
  private val material: Material
) : GameItem {

  private val prepared = Mutex()
  private lateinit var cube: Spatial

  override suspend fun prepare() {
    if (!this::cube.isInitialized) {
      cube = Geometry("cube_box", Box(SIZE, SIZE, SIZE)).apply {
        setMaterial(this@Cube.material)
      }
      number = number + 1
    }
  }

  override suspend fun representation(): Spatial {
    return cube
  }

  companion object {
    private const val SIZE = 1f
    var number = 0
  }
}

