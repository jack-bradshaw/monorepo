package com.jackbradshaw.sealant.source

import dagger.Binds
import dagger.Module

@Module
interface SealedSourceModule {
  @Binds fun bindSealedSourceFactory(impl: SealedSourceImpl.Factory): SealedSource.Factory
}
