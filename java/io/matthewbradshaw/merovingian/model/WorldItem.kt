package io.matthewbradshaw.merovingian.model

import com.jme3.scene.Spatial

interface WorldItem {
  suspend fun representation(): Spatial
  suspend fun logic() = Unit
}