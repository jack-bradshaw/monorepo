package io.matthewbradshaw.octavius

import kotlinx.coroutines.flow.Flow
import com.jme3.scene.Spatial

interface Game {
  fun ui(): Flow<Spatial>
}