package io.matthewbradshaw.gmonkey.model

interface Player {
  fun location(): Flow<Vector3f>
  fun ui(): Item
}