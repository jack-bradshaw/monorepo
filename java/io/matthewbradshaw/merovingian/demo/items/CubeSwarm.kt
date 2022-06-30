package io.matthewbradshaw.merovingian.demo.items

import io.matthewbradshaw.merovingian.model.WorldItem

interface CubeSwarm : WorldItem {
  suspend fun setCubeCount(count: Int)
}