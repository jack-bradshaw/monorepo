package java.io.matthewbradshaw.octavius

import dagger.Component
import io.matthewbradshaw.octavius.jmonkey.JMonkeyEngine
import io.matthewbradshaw.octavius.core.Game
import io.matthewbradshaw.octavius.jmonkey.JMonkeyEngineModule
import io.matthewbradshaw.octavius.jmonkey.JMonkeyAppModule
import dagger.BindsInstance
import io.matthewbradshaw.octavius.heartbeat.Ticker
import io.matthewbradshaw.octavius.heartbeat.TickerModule

@OctaviusScope
@Component(modules = [JMonkeyEngineModule::class, JMonkeyAppModule::class, TickerModule::class])
interface Octavius {

  fun ticker(): Ticker
  fun jMonkeyEngine(): JMonkeyEngine

  @Component.Builder
  interface Builder {
    @BindsInstance fun game(game: Game): Builder
    fun build(): Octavius
  }
}