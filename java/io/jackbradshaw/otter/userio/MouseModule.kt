package io.jackbradshaw.otter.userio

import dagger.Module
import dagger.Binds

@Module
interface MouseModule {
  @Binds
  fun bindMouse(impl: MouseImpl): Mouse
}