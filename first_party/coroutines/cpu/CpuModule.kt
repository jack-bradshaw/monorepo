package com.jackbradshaw.coroutines.cpu

import com.jackbradshaw.coroutines.Cpu
import dagger.Module
import dagger.Provides
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class CpuModule {
  @Provides
  @Cpu
  fun provideContext(@Cpu dispatcher: CoroutineDispatcher): CoroutineContext = dispatcher

  @Provides @Cpu fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
