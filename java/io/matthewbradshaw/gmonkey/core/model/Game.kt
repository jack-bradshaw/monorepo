package io.matthewbradshaw.gmonkey.core.model

import kotlinx.coroutines.flow.Flow
import com.jme3.scene.Spatial

interface Game {
  val paradigm: Paradigm
  fun ui(): Flow<Spatial>
}