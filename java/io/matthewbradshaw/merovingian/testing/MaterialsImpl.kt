package io.matthewbradshaw.merovingian.testing

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import javax.inject.Inject

@TestingScope
class MaterialsImpl @Inject internal constructor(
  private val assetManager: AssetManager,
) : Materials {
  override suspend fun createUnshadedGreen() = Material(
      assetManager,
      "Common/MatDefs/Misc/Unshaded.j3md"
    ).apply {
      setColor("Color", ColorRGBA.Green)
    }
}