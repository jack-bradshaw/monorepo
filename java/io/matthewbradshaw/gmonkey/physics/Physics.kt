package io.matthewbradshaw.gmonkey.physics

interface Physics {
  fun resting(): Set<Item>
  fun colissions(): Flow<Pair<Item>>
}