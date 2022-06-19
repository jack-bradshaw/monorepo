package io.matthewbradshaw.gmonkey.core.engine

import com.jme3.app.SimpleApplication
import com.jme3.renderer.Camera
import com.jme3.asset.AssetManager
import com.jme3.app.VRAppState

interface Engine {
  fun extractCamera(): Camera
  fun extractAssetManager(): AssetManager
  fun extractApp(): SimpleApplication
  fun extractVr(): VRAppState?
}