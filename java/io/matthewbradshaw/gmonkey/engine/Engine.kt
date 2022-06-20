package io.matthewbradshaw.gmonkey.engine

import com.jme3.app.SimpleApplication
import com.jme3.renderer.Camera
import com.jme3.asset.AssetManager
import com.jme3.app.VRAppState
import com.jme3.scene.Node

/**
 * The core elements of the [jMonkey 3 game engine](https://jmonkeyengine.org/).
 */
interface Engine {

  /**
   * Extracts the default camera from the engine.
   */
  fun extractCamera(): Camera

  /**
   * Extracts the asset manager from the engine.
   */
  fun extractAssetManager(): AssetManager

  /**
   * Extracts the root application object from the game engine.
   */
  fun extractApp(): SimpleApplication

  /**
   * Extracts the VR controller from the game engine. Returns null if the engine is not configured for VR.
   */
  fun extractVr(): VRAppState?

  /**
   * Extracts the root game node. Nodes attached to this node are displayed.
   */
  fun extractRootNode(): Node
}