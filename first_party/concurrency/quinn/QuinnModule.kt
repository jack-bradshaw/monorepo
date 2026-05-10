package com.jackbradshaw.concurrency.quinn

import dagger.Binds
import dagger.Module

/** Binds the production implementation of [Quinn.Factory]. */
@Module
interface QuinnModule {
  @Binds fun bindFactory(impl: QuinnImpl.FactoryImpl): Quinn.Factory
}
