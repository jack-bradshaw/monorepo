package io.jackbradshaw.jockstrap.demo.materials

import com.jme3.material.Material

interface Materials {
  suspend fun getRandomly(): Material
}