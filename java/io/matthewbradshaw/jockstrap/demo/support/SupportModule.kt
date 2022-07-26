package io.matthewbradshaw.jockstrap.demo.support

import dagger.Provides
import dagger.Module
import io.matthewbradshaw.jockstrap.demo.DemoScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @DemoScope
  fun provideRandom() = Random(0L)
}