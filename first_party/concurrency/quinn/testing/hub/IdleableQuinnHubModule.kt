package com.jackbradshaw.concurrency.quinn.testing.hub

import com.jackbradshaw.concurrency.quinn.Quinn
import dagger.Binds
import dagger.Module

@Module
interface IdleableQuinnHubModule {
  @Binds fun bindFactory(impl: IdleableQuinnHubImpl): Quinn.Factory

  @Binds fun bindIdleableHub(impl: IdleableQuinnHubImpl): IdleableQuinnHub
}
