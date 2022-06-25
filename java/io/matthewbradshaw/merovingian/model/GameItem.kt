package io.matthewbradshaw.merovingian.model

import io.matthewbradshaw.merovingian.lifecycle.Pausable
import io.matthewbradshaw.merovingian.lifecycle.Preparable
import com.jme3.scene.Spatial

interface GameItem{
  suspend fun representation(): Spatial

  suspend fun logic() = Unit
}