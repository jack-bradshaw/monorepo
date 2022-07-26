package io.matthewbradshaw.jockstrap.physics.experiment.items

import com.jme3.math.Vector3f
import io.matthewbradshaw.jockstrapmodel.world.WorldItem

interface Cube : WorldItem {
  suspend fun setA()
  suspend fun setB()
  suspend fun setCubeB(cube: Cube)
  suspend fun doTestThing()
}

 