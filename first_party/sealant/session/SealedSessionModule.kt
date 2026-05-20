package com.jackbradshaw.sealant.session

import dagger.Binds
import dagger.Module

@Module
interface SealedSessionModule {
  @Binds fun bindSealedSessionFactory(impl: SealedSessionImpl.FactoryImpl): SealedSession.Factory
}
