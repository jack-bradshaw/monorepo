package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import dagger.Binds
import dagger.Module

@Module
interface AdvancableDispatcherModule {
  @Binds fun bindAdvancableDispatcher(impl: AdvancableDispatcherImpl): AdvancableDispatcher
}
