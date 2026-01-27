package com.jackbradshaw.backstab.core.generator

import dagger.Module
import dagger.Provides

@Module
object MetaComponentGeneratorModule {
  @Provides
  fun provideMetaComponentGenerator(): MetaComponentGenerator = MetaComponentGeneratorImpl()
}
