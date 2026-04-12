package com.jackbradshaw.coroutines.testing.realistic.cpu

import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcher
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher

@Module
interface TestingCpuModule {

  @Binds @Cpu fun bindCpuContext(@Cpu dispatcher: IdleableDispatcher): CoroutineContext

  @Binds
  @Cpu
  fun bindCpuCoroutineDispatcher(@Cpu dispatcher: IdleableDispatcher): CoroutineDispatcher

  companion object {
    @Provides
    @CoroutinesDaggerScope
    @Cpu
    fun provideCpuDispatcher(dispatcher: IdleableDispatcherImpl): IdleableDispatcher = dispatcher
  }
}
