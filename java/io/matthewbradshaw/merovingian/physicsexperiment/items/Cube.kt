package io.matthewbradshaw.merovingian.physicsexperiment.items

import com.jme3.math.Vector3f
import io.matthewbradshaw.merovingian.model.world.WorldItem

interface Cube : WorldItem {
  suspend fun setA()
  suspend fun setB()
  suspend fun setCubeB(cube: Cube)
  suspend fun doTestThing()
}

 