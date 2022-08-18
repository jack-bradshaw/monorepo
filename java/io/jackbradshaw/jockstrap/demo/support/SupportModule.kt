package io.jackbradshaw.jockstrap.demo.support

import dagger.Provides
import io.jackbradshaw.jockstrap.demo.DemoScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @DemoScope
  fun provideRandom() = Random(0L)
}