package io.matthewbradshaw.octavius.engine

import com.jme3.app.SimpleApplication
import io.matthewbradshaw.octavius.core.Game
import com.jme3.renderer.Camera
import com.jme3.asset.AssetManager

interface Engine {
  suspend fun play(game: Game)
  fun camera(): Camera
  fun assetManager(): AssetManager
  fun application(): SimpleApplication
  fun context(): CoroutineContext
  fun vr(): VrAppState?
}