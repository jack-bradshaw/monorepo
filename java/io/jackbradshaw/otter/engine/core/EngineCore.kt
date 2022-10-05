package io.jackbradshaw.otter.engine.core

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.app.state.AppStateManager
import com.jme3.asset.AssetManager
import com.jme3.audio.AudioRenderer
import com.jme3.audio.Listener
import com.jme3.bullet.BulletAppState
import com.jme3.input.InputManager
import com.jme3.renderer.Camera
import com.jme3.renderer.RenderManager
import com.jme3.renderer.Renderer
import com.jme3.renderer.ViewPort
import com.jme3.scene.Node
import com.jme3.system.JmeContext
import com.jme3.system.Timer
import kotlinx.coroutines.CoroutineScope

/** The [jMonkey](https://wiki.jmonkeyengine.org/docs/3.4/documentation.html) game engine. */
interface EngineCore {

  /** Extracts the root application object. */
  fun extractApplication(): SimpleApplication

  /** Extracts the jMonkey context. */
  fun extractContext(): JmeContext

  /** Extracts the asset manager. */
  fun extractAssetManager(): AssetManager

  /** Extracts the state manager. */
  fun extractStateManager(): AppStateManager

  /** Extracts the input manager. */
  fun extractInputManager(): InputManager

  /** Extracts the render manager. */
  fun extractRenderManager(): RenderManager

  /** Extracts the video renderer. */
  fun extractVideoRenderer(): Renderer

  /** Extracts the audio renderer. */
  fun extractAudioRenderer(): AudioRenderer

  /** Extracts the default in-game camera. */
  fun extractDefaultInGameCamera(): Camera

  /** Extracts the default in-game microphone (otherwise known as the audio listener). */
  fun extractDefaultInGameMicrophone(): Listener

  /** Extracts the default view port. */
  fun extractDefaultViewPort(): ViewPort

  /** Extracts the XR system, null if the engine is not configured for XR. */
  fun extractXr(): VRAppState?

  /** Extracts the physics system. */
  fun extractPhysics(): BulletAppState

  /**
   * Extracts a node near the scene root for use by the framework internally. Framework consumers
   * should not modify or use this node and should instead use [extractGameNode] as their root node.
   */
  fun extractFrameworkNode(): Node

  /**
   * Extracts a node near the scene root for use by framework consumers. Framework consumers should
   * treat this as the root node in their games/applications.
   */
  fun extractGameNode(): Node

  /**
   * Extracts a coroutine scope which tracks the engine state. When the engine stops, the scope is
   * cancelled.
   */
  fun extractCoroutineScope(): CoroutineScope

  /** Extracts the timer. */
  fun extractTimer(): Timer

  /** Extracts the time between now and when the engine was started, measured in seconds. */
  fun extractTotalEngineRuntime(): Double
}
