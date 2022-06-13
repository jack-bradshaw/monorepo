package io.matthewbradshaw.octavius.ui

import com.jme3.scene.Spatial

interface Frameable {
  suspend fun scene(): Spatial
}