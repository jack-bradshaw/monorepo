package io.matthewbradshaw.jockstrap.demo.items

import com.jme3.math.Vector3f
import java.io.matthewbradshaw.jockstrap.entity.Entity

interface Cube : Entity {
  suspend fun setRelativePosition(position: Vector3f)
}

 