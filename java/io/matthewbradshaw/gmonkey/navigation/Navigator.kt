package io.matthewbradshaw.gmonkey.navigation

interface Navigator<L : Enum<L>> {
  fun switchTo(level: L)
  fun level(): Flow<L>
}