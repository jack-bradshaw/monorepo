package io.matthewbradshaw.merovingian.demo.support

import dagger.Provides
import dagger.Module
import io.matthewbradshaw.merovingian.demo.DemoScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @DemoScope
  fun provideRandom() = Random(0L)
}