package io.jackbradshaw.otter.userio

import dagger.Module
import dagger.Binds

@Module
interface KeyboardModule {
  @Binds
  fun bindKeyboard(impl: KeyboardImpl): Keyboard
}