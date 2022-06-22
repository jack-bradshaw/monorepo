package io.matthewbradshaw.merovingian.testing

import dagger.Provides
import dagger.Module
import kotlin.random.Random

@Module
class ExternalModule {
  @Provides
  @TestingScope
  fun provideRandom() = Random(0L)
}