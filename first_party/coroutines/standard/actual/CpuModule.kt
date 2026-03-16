package com.jackbradshaw.coroutines.standard.actual

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import com.jackbradshaw.coroutines.io.Cpu
import com.jackbradshaw.coroutines.io.CpuIntermediate

/** Provides types related to CPU-bound work. */
@Module
object CpuModule {
  @Provides
  @CpuIntermediate
  fun provideDispatcher() : CoroutineDispatcher {
    return Dispatchers.Default
  }

  @Provides
  @Cpu
  fun provideScope(@CpuIntermediate dispatcher: CoroutineDispatcher) : CoroutineScope {
    return CoroutineScope(dispatcher)
  }
}