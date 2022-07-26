package io.matthewbradshaw.jockstrap.physics.experiment.materials

import com.jme3.material.Material

interface Materials {
  suspend fun getRed(): Material
  suspend fun getBlue(): Material
  suspend fun getGreen(): Material
}