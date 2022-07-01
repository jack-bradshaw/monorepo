package io.matthewbradshaw.merovingian.demo.materials

import com.jme3.material.Material

interface Materials {
  suspend fun getRandomly(): Material
}