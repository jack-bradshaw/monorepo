package io.matthewbradshaw.gmonkey.host

interface Host {
  suspend fun host(game: Game)
}