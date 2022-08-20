package io.jackbradshaw.otter.physics

import io.jackbradshaw.otter.math.toJMonkeyQuaternion
import io.jackbradshaw.otter.math.toJMonkeyVector
import io.jackbradshaw.otter.math.tootterPoint
import io.jackbradshaw.otter.math.tootterQuaternion
import io.jackbradshaw.otter.math.tootterVector
import com.jme3.math.Transform as JmeTransform

/**
 * Creates a new JMonkey Engine 3 Transform which is equivalent to this vector.
 */
fun Placement.toJMonkeyTransform() =
  JmeTransform(position.toJMonkeyVector(), rotation.toJMonkeyQuaternion(), scale.toJMonkeyVector())

/**
 * Creates a new Merovingian Transform which is equivalent to this vector.
 */
fun JmeTransform.tootterPlacement() =
  placement(getTranslation().tootterPoint(), getRotation().tootterQuaternion(), getScale().tootterVector())