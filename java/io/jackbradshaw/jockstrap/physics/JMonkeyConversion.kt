package io.jackbradshaw.jockstrap.physics

import io.jackbradshaw.jockstrap.math.toJMonkeyQuaternion
import io.jackbradshaw.jockstrap.math.toJMonkeyVector
import io.jackbradshaw.jockstrap.math.toJockstrapPoint
import io.jackbradshaw.jockstrap.math.toJockstrapQuaternion
import io.jackbradshaw.jockstrap.math.toJockstrapVector
import com.jme3.math.Transform as JmeTransform

/**
 * Creates a new JMonkey Engine 3 Transform which is equivalent to this vector.
 */
fun Placement.toJMonkeyTransform() =
  JmeTransform(position.toJMonkeyVector(), rotation.toJMonkeyQuaternion(), scale.toJMonkeyVector())

/**
 * Creates a new Merovingian Transform which is equivalent to this vector.
 */
fun JmeTransform.toJockstrapPlacement() =
  placement(getTranslation().toJockstrapPoint(), getRotation().toJockstrapQuaternion(), getScale().toJockstrapVector())