package io.matthewbradshaw.frankl.demo.items

import com.jme3.math.Vector3f
import io.matthewbradshaw.frankl.model.LevelItem

interface Cube : LevelItem {
  suspend fun setRelativePosition(position: Vector3f)
}

 