package io.matthewbradshaw.octavius.driver

import com.jme3.app.SimpleApplication
import io.matthewbradshaw.octavius.core.Game
import com.jme3.renderer.Camera
import com.jme3.asset.AssetManager

interface Driver {
  suspend fun play(game: Game)
  fun extractCamera(): Camera
  fun extractAssetManager(): AssetManager
  fun extractApplication(): SimpleApplication
}