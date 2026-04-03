package com.jackbradshaw.coroutines.testing.realistic.cpu

import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcher
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
    // Uses 4 threads for testing to mirror common device CPUs.
    @dagger.Provides
    @com.jackbradshaw.coroutines.CoroutinesDaggerScope
    @com.jackbradshaw.coroutines.Cpu
    fun provideCpuDispatcher(factory: IdleableDispatcher.Factory): IdleableDispatcher =
        factory.create(4)
  }
}
