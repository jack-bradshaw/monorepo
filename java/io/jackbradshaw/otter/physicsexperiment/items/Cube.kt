package io.jackbradshaw.otter.physics.experiment.items

import io.jackbradshaw.ottermodel.world.WorldItem

interface Cube : WorldItem {
  suspend fun setA()
  suspend fun setB()
  suspend fun setCubeB(cube: Cube)
  suspend fun doTestThing()
}

 