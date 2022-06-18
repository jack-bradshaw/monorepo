package io.matthewbradshaw.merovingian.testing

import com.jme3.material.Material

interface Materials {
  suspend fun createUnshadedGreen(): Material
}