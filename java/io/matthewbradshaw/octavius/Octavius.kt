package io.matthewbradshaw.octavius

import dagger.Component
import io.matthewbradshaw.octavius.core.JMonkeyEngine
import io.matthewbradshaw.octavius.core.Game
import io.matthewbradshaw.octavius.jmonkey.JMonkeyModule
import dagger.BindsInstance
import io.matthewbradshaw.octavius.heartbeat.TickerModule

@OctaviusScope
@Component(modules = [JMonkeyModule::class, TickerModule::class])
interface Octavius {

  fun jMonkeyEngine(): JMonkeyEngine

  @Component.Builder
  interface Builder {
    @BindsInstance fun game(game: Game): Builder
    fun build(): Octavius
  }
}