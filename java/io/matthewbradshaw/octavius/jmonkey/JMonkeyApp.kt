package io.matthewbradshaw.octavius.jmonkey

import com.jme3.app.SimpleApplication
import kotlinx.coroutines.runBlocking
import io.matthewbradshaw.octavius.core.Game
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.MainScope
import com.jme3.renderer.Camera
import com.jme3.app.LostFocusBehavior
import io.matthewbradshaw.octavius.heartbeat.Ticker
import com.jme3.asset.AssetManager

interface JMonkeyApp {
  fun extractCamera(): Camera
  fun extractAssetManager(): AssetManager
  fun asSimpleApplication(): SimpleApplication
}