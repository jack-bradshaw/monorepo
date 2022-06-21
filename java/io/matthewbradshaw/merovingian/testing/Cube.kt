package io.matthewbradshaw.merovingian.testing

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

  private lateinit var cube: Spatial

  override suspend fun prepare() {
    if (this::cube.isInitialized) return
    cube = Geometry("box", Box(SIZE, SIZE, SIZE)).apply {
      setMaterial(material)
    }
  }

  override suspend fun representation() = cube

  companion object {
    private const val SIZE = 1f
  }
}

