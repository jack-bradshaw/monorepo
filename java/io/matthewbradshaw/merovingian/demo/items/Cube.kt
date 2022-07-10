package io.matthewbradshaw.merovingian.demo.items

import com.jme3.math.Vector3f
import io.matthewbradshaw.merovingian.model.LevelItem

interface Cube : LevelItem {
  suspend fun setRelativePosition(position: Vector3f)
}

 