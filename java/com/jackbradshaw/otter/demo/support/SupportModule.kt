package com.jackbradshaw.otter.demo.support

import com.jackbradshaw.otter.demo.DemoScope
import dagger.Module
import dagger.Provides
import kotlin.random.Random

@Module
class SupportModule {
  @Provides @DemoScope fun provideRandom() = Random(0L)
}
