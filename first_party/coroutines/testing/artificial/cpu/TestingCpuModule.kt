package com.jackbradshaw.coroutines.testing.artificial.cpu

import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.testing.artificial.dispatcher.AdvancableDispatcher
import dagger.Binds
import dagger.Module
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher

@Module
interface TestingCpuModule {
  @Binds @Cpu fun bindCpuContext(dispatcher: AdvancableDispatcher): CoroutineContext

  @Binds @Cpu fun bindCpuCoroutineDispatcher(dispatcher: AdvancableDispatcher): CoroutineDispatcher
}
