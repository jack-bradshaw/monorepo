package io.jackbradshaw.otter.demo.items

import com.jme3.math.Vector3f
import java.io.jackbradshaw.otter.entity.Entity

interface Cube : Entity {
  suspend fun setRelativePosition(position: Vector3f)
}
