package io.matthewbradshaw.merovingian.physics

interface Physics {
  fun resting(): Set<Item>
  fun colissions(): Flow<Pair<Item>>
}