package io.matthewbradshaw.gmonkey.host

@AutoFactory
class HostImpl(
  game: Game,
  @Provided @RootNode rootNode: Node
) {

  override suspend fun go() = coroutineScope {
    launch {
      var currentSpace: Space? = null

      // TODO fix up
      game
        .level()
        .map { it.space() }
        .onEach {
          currentSpace.pause()
        }
        .flatMap { it.items() }
        .onEach {
          detach(currentSpace)
          attach(it)
          currentSpace = it
        }
        .flatMapLatest { it.items() } {
          currentSpace?.let { detach(it) }
          currentSpace = it
          attach(currentSpace)
        }
    }

    launch {
      logic()
    }
  }

  private fun attach(space: Space) {

  }

  private fun detach(space: Space) {
    space.pause()
  }
}