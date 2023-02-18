package io.jackbradshaw.otter.demo.support

import dagger.Module
import dagger.Provides
import dagger.Module
import io.jackbradshaw.otter.demo.DemoScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides @DemoScope fun provideRandom() = Random(0L)
}
