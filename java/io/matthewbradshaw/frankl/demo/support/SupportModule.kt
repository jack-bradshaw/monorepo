package io.matthewbradshaw.frankl.demo.support

import dagger.Provides
import dagger.Module
import io.matthewbradshaw.frankl.demo.DemoScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @DemoScope
  fun provideRandom() = Random(0L)
}