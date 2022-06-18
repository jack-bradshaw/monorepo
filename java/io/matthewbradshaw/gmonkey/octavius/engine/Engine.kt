package io.matthewbradshaw.gmonkey.octavius.engine

import io.matthewbradshaw.gmonkey.octavius.Game
import com.jme3.app.SimpleApplication
import com.jme3.renderer.Camera
import com.jme3.asset.AssetManager
import com.jme3.app.VRAppState

interface Engine {
  suspend fun play(game: Game)
  fun camera(): Camera
  fun assetManager(): AssetManager
  fun root(): SimpleApplication
  fun vr(): VRAppState?
}