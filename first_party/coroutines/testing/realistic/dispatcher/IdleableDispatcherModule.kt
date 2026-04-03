package com.jackbradshaw.coroutines.testing.realistic.dispatcher

import dagger.Binds
import dagger.Module


@Module
interface IdleableDispatcherModule {
  @Binds fun bindFactory(impl: IdleableDispatcherImpl.Factory): IdleableDispatcher.Factory
}
