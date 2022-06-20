package io.matthewbradshaw.gmonkey.ui

interface Space : Item, Pausable, Preparable {
  fun contents(): Flow<Set<Item>>
}