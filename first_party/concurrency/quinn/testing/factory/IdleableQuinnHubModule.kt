package com.jackbradshaw.concurrency.quinn.testing.factory

import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import dagger.Binds
import dagger.Module

@Module
interface IdleableQuinnHubModule {
  @Binds fun bindFactory(impl: IdleableQuinnHubImpl): Quinn.Factory
  @Binds fun bindIdleableHub(impl: IdleableQuinnHubImpl): IdleableQuinn.Hub
}
