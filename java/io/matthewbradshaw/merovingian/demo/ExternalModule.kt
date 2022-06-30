package io.matthewbradshaw.merovingian.demo

import dagger.Provides
import dagger.Module
import kotlin.random.Random

@Module
class ExternalModule {
  @Provides
  @DemoScope
  fun provideRandom() = Random(0L)
}