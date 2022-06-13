package io.matthewbradshaw.octavius.jmonkey

import dagger.Module
import dagger.Binds

@Module
interface JMonkeyAppModule {
  @Binds
  fun bindJMonkeyApp(impl: JMonkeyAppImpl): JMonkeyApp
}