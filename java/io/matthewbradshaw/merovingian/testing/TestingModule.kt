package io.matthewbradshaw.merovingian.testing

import dagger.Module
import dagger.Provides
import kotlin.random.Random

@Module
class TestingModule {
  @Provides
  @TestingScope
  fun provideRandom() = Random(0L)
}