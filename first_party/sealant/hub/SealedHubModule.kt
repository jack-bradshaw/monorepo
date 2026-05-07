package com.jackbradshaw.sealant.hub

import dagger.Binds
import dagger.Module

@Module
interface SealedHubModule {
  @Binds fun bindSealedHubFactory(impl: SealedHubImpl.Factory): SealedHub.Factory
}
