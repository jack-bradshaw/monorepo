package io.matthewbradshaw.octavius.ui

import com.jme3.scene.Spatial

fun createFrameable(sceneBuilder: suspend () -> Spatial) = object : Frameable {
  override suspend fun scene() = sceneBuilder()
}