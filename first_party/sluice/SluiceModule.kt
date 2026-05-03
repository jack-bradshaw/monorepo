package com.jackbradshaw.sluice

import dagger.Binds
import dagger.Module

@Module
interface SluiceModule {
  @Binds
  fun bindSluiceFactory(impl: SluiceFactoryImpl): SluiceFactory
}
