package com.jackbradshaw.concurrency.quinn



import dagger.Binds
import dagger.Module

/** Binds the production implementation of [Quinn.Factory]. */
@Module
interface QuinnProductionModule {

  @Binds @Production fun bindFactory(impl: QuinnImpl.Factory): Quinn.Factory

}
