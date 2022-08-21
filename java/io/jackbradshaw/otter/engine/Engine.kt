package io.jackbradshaw.otter.engine

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.app.state.AppStateManager
import com.jme3.asset.AssetManager
import com.jme3.bullet.BulletAppState
import com.jme3.renderer.Camera
import com.jme3.scene.Node
import kotlinx.coroutines.CoroutineScope
import com.jme3.input.InputManager
import com.jme3.renderer.ViewPort

/**
 * Extracts elements from a jMonkey game engine.
 */
interface Engine {

  /**
   * Extracts the default camera.
   */
  fun extractDefaultCamera(): Camera

  /**
   * Extracts the view port.
   */
  fun extractViewPort(): ViewPort

  /**
   * Extracts the asset manager.
   */
  fun extractAssetManager(): AssetManager

  /**
   * Extracts the state manager.
   */
  fun extractStateManager(): AppStateManager

  /**
   * Extracts the input manager.
   */
  fun extractInputManager(): InputManager

  /**
   * Extracts the root application object.
   */
  fun extractApp(): SimpleApplication

  /**
   * Extracts the VR controller, null if the engine is not configured for VR.
   */
  fun extractVr(): VRAppState?

  /**
   * Extracts the physics controller.
   */
  fun extractPhysics(): BulletAppState

  /**
   * Extracts a node near the scene root for internal use by the framework. Framework consumers should not modify or use
   * this node and should instead use [extractGameNode].
   */
  fun extractFrameworkNode(): Node

  /**
   * Extracts a node near the scene root for use by framework consumers. Framework consumers should treat this as the
   * root node for their games.
   */
  fun extractGameNode(): Node

  /**
   * Extracts a coroutine scope which tracks the engine state, meaning it is cancelled when the game engine stops.
   */
  fun extractCoroutineScope(): CoroutineScope

  /**
   * The time since game start, measured in seconds.
   */
  fun extractTotalGameRuntime(): Double
}