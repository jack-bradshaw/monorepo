package com.jackbradshaw.sealant.hub

import com.jackbradshaw.sealant.session.SealedSessionModule
import dagger.Binds
import dagger.Module

@Module(includes = [SealedSessionModule::class])
interface SealedHubModule {
  @Binds fun bindSealedHubFactory(impl: SealedHubImpl.Factory): SealedHub.Factory
}
