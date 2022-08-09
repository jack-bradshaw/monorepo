package io.jackbradshaw.jockstrap.demo.items

import com.jme3.math.Vector3f
import java.io.jackbradshaw.jockstrap.entity.Entity

interface Cube : Entity {
  suspend fun setRelativePosition(position: Vector3f)
}

 